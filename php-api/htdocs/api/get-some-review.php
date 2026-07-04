<?php
header("Content-Type: application/json");
require_once("config.php");

$sql = "SELECT 
        cr.id,
        cr.rating,
        cr.comment,
        cr.created_at,
        c.title AS course_name,
        u.username AS reviewer_name
    FROM course_review cr
    JOIN course c ON cr.course_id = c.id
    JOIN user u ON cr.user_id = u.id;";
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
