<?php
header("Content-Type: application/json");
require_once("config.php");
require_once("/vendor/autoload.php");
use Firebase\JWT\JWT;

$secret_key = "abc"; // secret key
$issued_at = time();
$expiration_time = $issued_at + 3600; // 1 hour

// get input
//$input = json_decode(file_get_contents("php://input"), true);
//if (!isset($input["userid"]) || !isset($input["password"])) {
//    echo json_encode(["error" => "Missing id or password"]);
//    exit();
//}
//$id = $input["id"];
//$password = $input["password"];

$id = $_POST["id"];
$password = $_POST["password"];

// check id and password
$stmt = $conn->prepare("SELECT * FROM user WHERE id = ? AND password = ? AND is_active = 1");
$stmt->bind_param("ss", $id, $password);
$stmt->execute();
$result = $stmt->get_result();

// if id and password is correct
if ($result->num_rows > 0) {
    // generate jwt
    $payload = [
        "iss" => "localhost",
        "iat" => $issued_at,
        "exp" => $expiration_time,
        "user_id" => $id
    ];
    $jwt = JWT::encode($payload, $secret_key, 'HS256');

    //$data = [];
    //while ($row = $result->fetch_assoc()) {
    //   $data[] = $row;
    //}
    //$id = $data[0];

    // update last login
    $update_sql = "UPDATE user SET last_login = NOW() WHERE id = ?";
    $update_stmt = $conn->prepare($update_sql);
    $update_stmt->bind_param("s", $id);
    $update_stmt->execute();


    echo json_encode(["status" => "success", "token" => $jwt, "message" => "login successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "login failed"]);
}
?>
