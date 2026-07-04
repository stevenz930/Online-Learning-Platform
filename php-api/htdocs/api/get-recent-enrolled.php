<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_GET['id'];


// SELECT course.id, title, description, price, created_at, updated_at, is_published, level, thumbnail, 
//    avg_rating, review_count, user.username AS instructor_name, category, subject, tags, e.enrolled_at,
//   COUNT(lp.id) AS total_lessons,
//   SUM(CASE WHEN lp.status = 'completed' THEN 1 ELSE 0 END) AS completed_lessons,
//   COUNT(lp.id) - SUM(CASE WHEN lp.status = 'completed' THEN 1 ELSE 0 END) AS remaining_lessons,
//   CONCAT(
//     ROUND(SUM(CASE WHEN lp.status = 'completed' THEN 1 ELSE 0 END) / COUNT(lp.id) * 100, 1),
//     '%'
//   ) AS progress_percent
// FROM enrollment e
// JOIN course ON e.course_id = course.id
// JOIN user ON course.user_id = user.id
// JOIN lesson_progress lp ON lp.course_id = course.id AND lp.user_id = e.user_id
// WHERE e.user_id = 1
// GROUP BY e.course_id
// ORDER BY MIN(e.enrolled_at) ASC;

// SELECT course.id, title, description, price, created_at, updated_at, is_published, level, thumbnail, avg_rating, review_count,
//          user.username AS instructor_name, category, subject, tags, e.enrolled_at
//       FROM enrollment e
//       JOIN course ON e.course_id = course.id
//       JOIN user ON course.user_id = user.id
//       WHERE e.user_id = ?
//       GROUP BY e.course_id
//       ORDER BY MIN(e.enrolled_at) ASC;

if ($id != null) {
   $sql = "SELECT course.id, title, description, price, created_at, updated_at, is_published, level, thumbnail, 
         avg_rating, review_count, user.username AS instructor_name, category, subject, tags, e.enrolled_at,
         COUNT(lp.id) AS total_lessons,
         SUM(CASE WHEN lp.status = 'completed' THEN 1 ELSE 0 END) AS completed_lessons,
         COUNT(lp.id) - SUM(CASE WHEN lp.status = 'completed' THEN 1 ELSE 0 END) AS remaining_lessons,
         CONCAT(
            ROUND(SUM(CASE WHEN lp.status = 'completed' THEN 1 ELSE 0 END) / COUNT(lp.id) * 100, 0)
         ) AS progress_percent
      FROM enrollment e
      JOIN course ON e.course_id = course.id
      JOIN user ON course.user_id = user.id
      JOIN lesson_progress lp ON lp.course_id = course.id AND lp.user_id = e.user_id
      WHERE e.user_id = ?
      GROUP BY e.course_id
      ORDER BY MIN(e.enrolled_at) ASC;";
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