<?php
header("Content-Type: application/json");
require_once("config.php");

$sql = "SELECT id, username, password, email, last_login, date_joined, is_superuser, is_instructor, is_active FROM user";
$result = $conn->query($sql);

$data = [];
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

if (!empty($data)) {
    echo json_encode(["status" => "success", "data" => $data]);
} else {
    echo json_encode(["status" => "error", "message" => "No data found"]);
}
?>
