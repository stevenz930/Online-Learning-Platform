<?php
header("Content-Type: application/json");
require_once("config.php");

$keyword = '%' . $_GET['keyword'] . '%';

if ($keyword != null) {
    $sql = "SELECT course.id, title, description, price, created_at, updated_at,level,thumbnail,avg_rating,review_count, user.username as instructor_name, category, subject
    FROM course, user
    where user.id = course.user_id and (
      title LIKE ? OR 
      description LIKE ? OR
      tags LIKE ? OR
      category LIKE ? OR 
      subject LIKE ?);";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssss", $keyword, $keyword, $keyword, $keyword, $keyword);
    
    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];
    while ($row = $result->fetch_assoc()) {
       $data[] = $row;
    }

    if (!empty($data)) {
       echo json_encode(["status" => "success", "data" => $data]);
    } else {
       echo json_encode(["status" => "error", "message" => "keyword not found"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid keyword"]);
}
?>
