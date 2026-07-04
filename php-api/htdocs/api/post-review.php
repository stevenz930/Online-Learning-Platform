<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_POST["id"];
$course_id = $_POST["course_id"];
$rating = $_POST["rating"];
$comment = $_POST["comment"];

if ($id != null && $course_id != null && $rating != null && $comment != null) {
    // sign up
    $sql = "INSERT INTO course_review (course_id, user_id, rating, comment, created_at)
    VALUES (?, ?, ?, ?, NOW())
    ON DUPLICATE KEY UPDATE
      rating = VALUES(rating),
      comment = VALUES(comment),
      created_at = NOW();";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssss", $course_id, $id, $rating, $comment);
    $stmt->execute();


    echo json_encode(["status" => "success", "message" => "post review successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "post review failed"]);
}
?>