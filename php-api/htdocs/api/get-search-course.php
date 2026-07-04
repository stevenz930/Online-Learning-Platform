<?php
header("Content-Type: application/json");
require_once("config.php");

$course = "%" . $_GET['course'] . "%";

if ($course != null) {
    $sql = "SELECT distinct course.id, title, description, price, created_at, updated_at,level,thumbnail,avg_rating,review_count, user.username as instructor_name, course_category.name as category, course_subject.name as subject
    FROM course, user, course_category, course_subject
    where user.id = course.user_id and 
        course_category.id = course.category_id and 
        course_category.subject_id = course_subject.id and
        course.title like ?
    ORDER BY id;";
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