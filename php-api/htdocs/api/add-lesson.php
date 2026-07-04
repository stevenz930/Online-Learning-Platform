<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
//$input = $_POST;
if (!isset($input)) {
    echo json_encode(["error" => "Empty Input", "input data" => $input]);
    exit();
}

$insert_sql = "INSERT INTO lesson (
        title, lesson_order, content, 
        course_id, duration, video_url,
        cover) 
    values (?,?,?,
        ?,?,?,
        ?)";
$insert_stmt = $conn->prepare($insert_sql);
$insert_stmt->bind_param("sisisss", 
    $input['title'], 
    $input['lesson_order'], 
    $input['content'], 
    $input['course_id'], 
    $input['duration'], 
    $input['video_url'], 
    $input['cover']
);
$insert_stmt->execute();

echo json_encode(["status" => "success", "data" => $input['title']]);

?>