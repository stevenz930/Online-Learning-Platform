<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_POST["id"];

if ($id != null) {
    // sign up
    $sql = "UPDATE user
        SET is_instructor = 1
        WHERE id = ?;";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $id);
    $stmt->execute();


    echo json_encode(["status" => "success", "message" => "be instructor successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "be instructor failed"]);
}
?>