<?php
header("Content-Type: application/json");

$data = [
    "status" => "success",
    "message" => "Hello, this is a simple REST API response!123",
    "time" => date("Y-m-d H:i:s")
];

echo json_encode($data);
?>