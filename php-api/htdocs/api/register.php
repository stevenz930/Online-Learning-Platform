<?php
header("Content-Type: application/json");
require_once("config.php");

$username = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];

if ($username != null && $password != null && $email != null) {
    // sign up
    $sql = "INSERT user SET username = ?, password = ?, email = ?, date_joined = NOW()";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sss", $username, $password, $email);
    $stmt->execute();


    echo json_encode(["status" => "success", "message" => "sign up successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "sign up failed"]);
}
?>