<?php
header("Content-Type: application/json");
require_once("config.php");

$sql = "SELECT course.id, title, description, price, created_at, updated_at,level,thumbnail,avg_rating,review_count, user.username as instructor_name, course_category.name as category
FROM course, user, course_category 
where user.id = course.user_id and course_category.id = course.category_id
order by course.id";
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
