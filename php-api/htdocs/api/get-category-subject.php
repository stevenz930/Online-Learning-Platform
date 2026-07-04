<?php
header("Content-Type: application/json");
require_once("config.php");

$sql = "SELECT cc.id, cc.name as `category_name`, cs.name as `subject_name` 
FROM `course_category` as cc, `course_subject` as cs 
where cc.subject_id = cs.id";
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
