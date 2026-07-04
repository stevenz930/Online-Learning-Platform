<?php
header("Content-Type: application/json");
require_once("config.php");

//$subject = isset($_GET['subject']) ? intval($_GET['subject']) : null;
$subject = $_GET['subject'];

if ($subject != null) {
    //$sql = "SELECT course.id, title, description, price, created_at, updated_at,level,thumbnail,avg_rating,review_count, user.username as instructor_name, course_category.name as category, course_subject.name as subject
    //FROM course, user, course_category, course_subject
    //where user.id = course.user_id and 
    //    course_category.id = course.category_id and 
    //    course_category.subject_id = course_subject.id and
    //    course_subject.name = ?
    //ORDER BY review_count DESC, id ASC
    //LIMIT 10;";
    $sql = "SELECT course.id, title, description, price, created_at, updated_at,level,thumbnail,avg_rating,review_count, user.username as instructor_name, category, subject
    FROM course, user, course_category, course_subject
    where user.id = course.user_id and 
        course_category.id = course.category_id and 
        course_category.subject_id = course_subject.id and
        course_subject.name = ?
    ORDER BY course.avg_rating DESC, id ASC
    LIMIT 10;";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $subject);
    
    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];
    while ($row = $result->fetch_assoc()) {
       $data[] = $row;
    }

    if (!empty($data)) {
       echo json_encode(["status" => "success", "data" => $data]);
    } else {
       echo json_encode(["status" => "error", "message" => "subject name not found $subject"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid subject name"]);
}
?>