<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_POST["id"];
$course_id = $_POST["course_id"];

if ($id != null && $course_id != null) {
    // sign up
    $sql = "INSERT INTO cart_item (price, course_id, user_id)
        SELECT price, ?, ?
        FROM course
        WHERE id = ?
        ON DUPLICATE KEY UPDATE
          price = VALUES(price);";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sss", $course_id, $id, $course_id);
    $stmt->execute();


    echo json_encode(["status" => "success", "message" => "add to cart successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "add to cart failed"]);
}
?>