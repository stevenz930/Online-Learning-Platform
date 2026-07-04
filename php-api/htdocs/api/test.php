<?php
// 接收名为 'subject' 的参数
if (isset($_GET['subject'])) {
    $subject = $_GET['subject'];

    // 进行简单验证，例如判断是否为空或是否在允许的列表中
    $allowedSubjects = ['AI', 'Cybersecurity', 'DataScience'];
    if (in_array($subject, $allowedSubjects)) {
        // 正常处理
        echo json_encode([
            'status' => 'success',
            'message' => 'You requested the subject: ' . $subject
        ]);
    } else {
        // 错误处理
        echo json_encode([
            'status' => 'error',
            'message' => 'Invalid subject name'
        ]);
    }
} else {
    echo json_encode([
        'status' => 'error',
        'message' => 'Missing subject parameter'
    ]);
}
?>
