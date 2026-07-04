<?php
header("Content-Type: application/json");
require_once("config.php");
require_once("/vendor/autoload.php");
use Firebase\JWT\JWT;
use Firebase\JWT\Key;

$secret_key = "abc";
$headers = getallheaders();
//echo json_encode(["status" => "test", "headers" => $headers, "message" => "test php jwt"]);
$jwt = $headers["Authorization"];
if (!isset($jwt)) {
    //http_response_code(401);
    echo json_encode(["status" => "error", "message" => "error"]);
    //exit();
}

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

try {
    $decoded = JWT::decode($jwt, new Key($secret_key, 'HS256'));
    echo json_encode(["status" => "success", "token" => $jwt, "message" => "logout success"]);
} catch (Exception $e) {
    http_response_code(401);
    echo json_encode(["status" => "error", "token" => $jwt, "message" => "error"]);
}
?>
