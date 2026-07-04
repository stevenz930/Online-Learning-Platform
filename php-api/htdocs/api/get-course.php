<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_GET['id'];
$course = $_GET['course_id'];

if ($id != null && $course != null) {
    $sql1 = "SELECT course.id, title, description, price, created_at, updated_at,level,thumbnail,avg_rating,review_count, user.username as instructor_name, category, subject
    FROM course, user, course_category, course_subject
    where user.id = course.user_id and 
        course_category.id = course.category_id and 
        course_category.subject_id = course_subject.id and
        course.id like ?
    ORDER BY id;";
    $stmt1 = $conn->prepare($sql1);
    $stmt1->bind_param("s", $course);
    
    $stmt1->execute();
    $result1 = $stmt1->get_result();

    $data1 = [];
    while ($row1 = $result1->fetch_assoc()) {
       $data1[] = $row1;
    }


    $sql2 = "SELECT EXISTS (
            SELECT 1
            FROM enrollment
            WHERE course_id = ? AND user_id = ?
        ) AS has_enrolled;";
    $stmt2 = $conn->prepare($sql2);
    $stmt2->bind_param("ss", $course, $id);
    $stmt2->execute();
    $result2 = $stmt2->get_result();
    $data2 = [];
    while ($row2 = $result2->fetch_assoc()) {
       $data2[] = $row2;
    }


    $sql3 = "SELECT EXISTS (
            SELECT 1
            FROM cart_item
            WHERE course_id = ? AND user_id = ?
        ) AS is_in_cart;";
    $stmt3 = $conn->prepare($sql3);
    $stmt3->bind_param("ss", $course, $id);
    $stmt3->execute();
    $result3 = $stmt3->get_result();
    $data3 = [];
    while ($row3 = $result3->fetch_assoc()) {
       $data3[] = $row3;
    }


    if (!empty($data1) && !empty($data2)) {
        echo json_encode(["status" => "success", 
            "data" => $data1, 
            "has_enrolled" => $data2[0]['has_enrolled'],
            "is_in_cart" => $data3[0]['is_in_cart']
        ]);
    } else {
       echo json_encode(["status" => "error", "message" => "course name not found $course"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Invalid course name"]);
}
?>