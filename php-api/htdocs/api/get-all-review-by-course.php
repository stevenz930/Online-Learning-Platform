<?php
header("Content-Type: application/json");
require_once("config.php");

$course = $_GET['id'];

if ($course != null) {
    $sql = "SELECT course_review.id, rating, comment, created_at, course_id, user_id, user.username, user.avatar
    FROM course_review, user
    WHERE course_id = ? and course_review.user_id = user.id
    ORDER BY created_at DESC;";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $course);
    
    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];
    while ($row = $result->fetch_assoc()) {
       $data[] = $row;
    }

    if (!empty($data)) {
       echo json_encode(["status" => "success", "data" => $data]);
    } else {
       echo json_encode(["status" => "error", "message" => "course name not found $course"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid course name"]);
}
?>