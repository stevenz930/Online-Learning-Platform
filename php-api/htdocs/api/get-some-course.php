<?php
header("Content-Type: application/json");
require_once("config.php");

$sql = "SELECT 
        course.id,
        course.title,
        course.price,
        course.thumbnail,
        course.created_at,
        course.updated_at,
        user.username AS instructor_name
    FROM course
    JOIN user ON course.user_id = user.id;";
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
