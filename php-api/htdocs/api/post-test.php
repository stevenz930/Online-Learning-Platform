<?php
header("Content-Type: application/json");
require_once("config.php");

$data = json_decode(file_get_contents("php://input"), true);
if (!isset($data["username"]) || !isset($data["password"])) {
    echo json_encode(["error" => "Missing username or password"]);
    exit();
}

$stmt = $conn->prepare("INSERT INTO test (username, password) VALUES (?, ?)");
$stmt->bind_param("ss", $data["username"], $data["password"]);

if ($stmt->execute()) {
    echo json_encode(["success" => "Insert successful"]);
} else {
    echo json_encode(["error" => "Insert error" . $stmt->error]);
}
?>
