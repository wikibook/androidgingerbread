<?php
//-- �����ڵ� --------------------------------------------------------------------------------------------------
$host = "localhost";
$user = "soudz";
$pass = "artofcode";
$db = "soudz";

$connection = mysql_connect($host, $user, $pass);
mysql_select_db($db);

switch ($_GET[mode]) {
  case "send":
    send_message($_GET[ip_address], $_GET[message], $connection);
    break;
  case "register":
    register_user($_GET[ip_address], $_GET[registration_id], $connection);
    break;
  case "unregister":
    unregister_user($_GET[ip_address], $_GET[registration_id], $connection);
    break;
  case "read":
    read_message($_GET[id], $connection);
    break;
}

mysql_close($connection);

//-- �Լ��� --------------------------------------------------------------------------------------------------
function register_user($ip_address, $registration_id, $connection) {
  $query = "SELECT COUNT(*) AS count FROM cm_user where ip_address = '$ip_address'";
  $result_set = mysql_query($query, $connection);
  $rows = mysql_fetch_array($result_set);
  
  if ($rows['count'] == 0) {
    $query = "INSERT INTO cm_user(registration_id, ip_address) VALUES('$registration_id', '$ip_address')";
    mysql_query($query, $connection);
    echo $query;
  } else {
    $query = "UPDATE cm_user SET registration_id = '$registration_id' where ip_address = '$ip_address'";
    mysql_query($query, $connection);
  }
}

function unregister_user($ip_address, $registration_id, $connection) {
  $query = "DELETE FROM cm_user WHERE ip_address = '$ip_address'";
  mysql_query($query, $connection);
}

function send_message($ip_address_from, $message, $connection) {

  // DB�� �޽��� �����ϱ�. �׸��� �� ���̵� ���� ����صд�.
  $query = "SELECT (MAX(id)+1) AS new_id from cm_message";
  $result_set = mysql_query($query, $connection);
  $rows = mysql_fetch_array($result_set);
  $new_id = ($rows[new_id] == '') ? 1 : $rows[new_id];
  $query = "INSERT INTO cm_message(id, ip_address_from, message) VALUES($new_id, '$ip_address_from', '$message')";
  mysql_query($query);

  // DB�� ����� ��� ���̵� �������� - �̶�, �޽����� ���� �ܸ��� ������ ��ο��� �޽����� ������.
  $query = "SELECT registration_id FROM cm_user WHERE ip_address <> '$ip_address_from'";
  $result_set = mysql_query($query, $connection);
  $count = mysql_affected_rows();
  if ($count == 0)
    return;
  $total = mysql_affected_rows();

  // ������ ���̵� ����Ͽ� ������ ����Ű�� �޾ƾ� �Ѵ�.
  $data = "&accountType=HOSTED_OR_GOOGLE&Email=esoudz@gmail.com&Passwd=<Password>&service=ac2dm&source=test-1.0";
  $host = "www.google.com";
  $path = "/accounts/ClientLogin";
  $fp = fsockopen("ssl://".$host, 443, $errno, $errstr, 30);
  if($fp)
  {
    fputs($fp, "POST $path HTTP/1.0\r\n");
    fputs($fp, "Host: $host\r\n");
    fputs($fp, "User-Agent: PHP Script\r\n");
    fputs($fp, "Content-Type: application/x-www-form-urlencoded\r\n");
    fputs($fp, "Content-Length: " . strlen($data) . "\r\n");
    fputs($fp, "Connection: close\r\n\r\n");
    fputs($fp, $data . "\r\n\r\n");
    $data = '';
    while (!feof($fp)) {
      $data .= fgets($fp);
    }
    fclose($fp);
  } else {
    echo "$errstr ($errno)\n";
    return 0;
  }

  $response = split("\r\n\r\n", $data);
  $header = $response[0];
  $responsecontent = $response[1];

  // ���� �߿��� �κ�. �̺κ��� ������ ������ Ȯ���� 100%
  if(!(strpos($header,"Transfer-Encoding: chunked")===false)){
    $aux=split("\r\n",$responsecontent);
    for($i=0;$i<count($aux);$i++)
      if($i==0 || ($i%2==0))
        $aux[$i]="";
    $responsecontent=implode("",$aux);
  }

  $tmp = split("Auth=",$responsecontent);
  $result = $tmp[1]; // ���������� ���� ���� ���� Ű

  for ($i = 0; $i < $total; $i++) {
    $rows = mysql_fetch_array($result_set);
    $registration_id = $rows[registration_id];

    $auth = $registration_id;
    $data = "registration_id=".$auth."&collapse_key=1&data.new_id=$new_id";
    $host = "android.apis.google.com";

    $path = "/c2dm/send";
    $fp = fsockopen("ssl://".$host, 443, $errno, $errstr, 30);

    if($fp)
    {
      fputs($fp, "POST $path HTTP/1.0\r\n");
      fputs($fp, "Host: $host\r\n");
      fputs($fp, "Content-Type: application/x-www-form-urlencoded\r\n");
      fputs($fp, "Content-Length: " . strlen($data) . "\r\n");
      fputs($fp, "Authorization: GoogleLogin auth={$result}\r\n");
      fputs($fp, $data . "\r\n\r\n");
      $data = '';

      /* 
      ��½ÿ� �Ʒ��� ���� �޽����� ������ �����̴�. �ƹ��� ���ϵ� �������ϸ� Request �����ÿ� ������ �ִ� ���̴�.

      HTTP/1.0 200 OK Content-Type: text/plain Date: Thu, 17 Feb 2011 16:59:19 GMT Expires: Thu, 17 Feb 2011 16:59:19 
      GMT Cache-Control: private, max-age=0 X-Content-Type-Options: nosniff X-Frame-Options: 
      SAMEORIGIN X-XSS-Protection: 1; mode=block Server: GSE id=0:1297961959096359%11d589e600000031

      ���� �׽�Ʈ�ϰ� �ʹٸ�... c2dm_messenger.php ������ ���������� �Ʒ��� ���� �������� �����غ���.
      (����, registration_id ���� �ڽ��� ���� ����� ��)
      http://android.artofcode.org/c2dm_messenger.php?registration_id=<��Ͼ��̵�>&ip_address=00:26:37:8F:A9:1C&mode=send&message=gvjkd

      �׸��� ���� �Ʒ��� �ּ��� Ǯ�� �ٶ���.
      */
      /*while (!feof($fp)) {
        $data .= fgets($fp, 4096);
      }
      echo $data;*/
      
      fclose($fp);
    } else {
      echo "$errstr ($errno)\n";
      return 0;
    }
  } // end for
}

function read_message($id, $connection)
{
  $query = "SELECT * FROM cm_message WHERE id = '$id'";
  $result_set = mysql_query($query, $connection);
  $count = mysql_affected_rows();
  if ($count == 0) {
    return;
  } else {
    $rows = mysql_fetch_array($result_set);
    echo $rows[ip_address_from]."\n".$rows[message];
  }
}
?>
