<?php
header("Content-Type: application/json");
require_once("config.php");

$user_id = json_decode(file_get_contents("php://input"), true);

if ($user_id != null) {
    $sql = "SELECT username, avatar FROM user WHERE id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];
    while ($row = $result->fetch_assoc()) {
       $data[] = $row;
    }
    $avatar = $data[0]['avatar'];
    $username = $data[0]['username'];

    if (!empty($data)) {
       echo json_encode(["status" => "success", "avatar" => $avatar, "username" => $username]);
    } else {
       echo json_encode(["status" => "error", "message" => "User not found"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid user ID"]);
}
?>