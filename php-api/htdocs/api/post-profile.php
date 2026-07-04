<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_POST["id"];
$username = $_POST["username"];
$email = $_POST["email"];
$avatar = $_POST["avatar"];
$bio = $_POST["bio"];
$facebook_url = $_POST["facebook_url"];
$instagram_url = $_POST["instagram_url"];
$linkedin_url = $_POST["linkedin_url"];
$twitter_url = $_POST["twitter_url"];

if ($id != null && $username != null && $email != null && $avatar != null && $bio != null &&
    $facebook_url != null && $instagram_url != null && $linkedin_url != null && $twitter_url != null) {
    $sql = "UPDATE user
        SET 
            username = ?,
            email = ?,
            avatar = ?,
            bio = ?,
            facebook_url = ?,
            instagram_url = ?,
            linkedin_url = ?,
            twitter_url = ?
        WHERE id = ?;";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssssss", $username, $email, $avatar, $bio, $facebook_url, $instagram_url, $linkedin_url, $twitter_url, $id);
    $stmt->execute();

    echo json_encode(["status" => "success", "message" => "update profile successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "update profile failed"]);
}
?>