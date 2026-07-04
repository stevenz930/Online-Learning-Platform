<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_GET['id'];

if ($id != null) {
    $sql = "SELECT ci.id, ci.price, ci.course_id, ci.user_id,
          course.title, course.thumbnail, user.username AS instructor_name
        FROM cart_item ci
        JOIN course ON ci.course_id = course.id
        JOIN user ON course.user_id = user.id
        WHERE ci.user_id = ?;";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $id);
    
    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];
    while ($row = $result->fetch_assoc()) {
       $data[] = $row;
    }

    if (!empty($data)) {
       echo json_encode(["status" => "success", "data" => $data]);
    } else {
       echo json_encode(["status" => "error", "message" => "user id not found $id"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid user id"]);
}
?>