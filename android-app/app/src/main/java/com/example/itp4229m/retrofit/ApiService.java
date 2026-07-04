package com.example.itp4229m.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Query;

public interface ApiService {
    //@GET("get-all-user")
    //Call<ApiResponse<List<User>>> getAllUsers();

    //@GET("get-all-instructor")
    //Call<ApiResponse<List<User>>> getAllInstructors();

    @FormUrlEncoded
    @POST("login-user")
    Call<ApiResponse<User>> loginUser(@Field("id") String userid, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ApiResponse<User>> registerUser(@Field("username") String username, @Field("email") String email, @Field("password") String password);

    @GET("get-user")
    Call<ApiResponse<List<User>>> getUser(@Query("id") String userId);

    //@GET("get-all-course")
    //Call<ApiResponse<List<Course>>> getAllCourses();

    //@GET("get-category-subject")
    //Call<ApiResponse<List<CategorySubject>>> getAllCategorySubject();

    @GET("get-all-subject")
    Call<ApiResponse<List<Subject>>> getAllSubjects();

    @GET("get-top10-popular-course")
    Call<ApiResponse<List<Course>>> getTop10Courses();

    //@GET("get-top10-popular-course-by-subject")
    //Call<ApiResponse<List<Course>>> getTop10CoursesBySubject(@Query("subject") String subject);

    @GET("get-top10-rating-course")
    Call<ApiResponse<List<Course>>> getTop10RatingCourses();

    @GET("get-top10-rating-course-by-subject")
    Call<ApiResponse<List<Course>>> getTop10RatingCoursesBySubject(@Query("subject") String subject);

    @GET("get-course")
    Call<ApiResponse<List<Course>>> getCourse(@Query("id") String userId, @Query("course_id") String courseId);

    @GET("get-all-lesson-by-course")
    Call<ApiResponse<List<Lesson>>> getAllLessonByCourse(@Query("id") String courseId);

    @GET("get-all-review-by-course")
    Call<ApiResponse<List<Review>>> getAllReviewByCourse(@Query("id") String courseId);

    @GET("get-lesson")
    Call<ApiResponse<List<Lesson>>> getLesson(@Query("id") String courseId, @Query("lesson_order") String lessonOrder);

    @GET("search")
    Call<ApiResponse<List<Course>>> search(@Query("keyword") String keyword);

    @GET("get-recent-enrolled")
    Call<ApiResponse<List<Course>>> getRecentEnrolledCourses(@Query("id") String userId);

    @FormUrlEncoded
    @POST("post-review")
    Call<ApiResponse<Review>> postReview(@Field("id") String userId, @Field("course_id") String courseId,
                                         @Field("rating") float rating, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("post-enrollment")
    Call<ApiResponse<Enrollment>> postEnrollment(@Field("id") String userId, @Field("course_id") String courseId);

    @GET("get-cart-by-user")
    Call<ApiResponse<List<Cart>>> getCart(@Query("id") String userId);

    @FormUrlEncoded
    @POST("delete-cart-item")
    Call<ApiResponse<Cart>> deleteCartItem(@Field("id") String userId, @Field("course_id") String courseId);

    @FormUrlEncoded
    @POST("post-add-to-cart")
    Call<ApiResponse<Cart>> postAdd2Cart(@Field("id") String userId, @Field("course_id") String courseId);

    @FormUrlEncoded
    @POST("post-be-instructor")
    Call<ApiResponse<Cart>> postBeInstructor(@Field("id") String userId);

    @FormUrlEncoded
    @POST("post-profile")
    Call<ApiResponse<User>> postProfile(@Field("id") String userId, @Field("username") String username,
                                        @Field("email") String email, @Field("avatar") String avatar,
                                        @Field("bio") String bio, @Field("facebook_url") String facebookUrl,
                                        @Field("instagram_url") String instagramUrl, @Field("linkedin_url") String linkedinUrl,
                                        @Field("twitter_url") String twitterUrl);

}



