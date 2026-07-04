<?php
header("Content-Type: application/json");
require_once("config.php");

$review_id = isset($_GET['id']) ? intval($_GET['id']) : null;

if ($review_id != null) {
    $sql = "SELECT * FROM course WHERE id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $review_id);
    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];
    while ($row = $result->fetch_assoc()) {
       $data[] = $row;
    }

    if (!empty($data)) {
       echo json_encode(["status" => "success", "data" => $data]);
    } else {
       echo json_encode(["status" => "error", "message" => "User not found"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid user ID"]);
}
?>