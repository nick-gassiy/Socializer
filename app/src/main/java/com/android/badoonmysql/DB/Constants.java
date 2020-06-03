package com.android.badoonmysql.DB;

public class Constants {

    //private static final String ROOT_URL = "http://192.168.2.101/badoon/v1/";
    private static final String ROOT_URL = "http://194.67.109.125/v1/";

    // Users
    public static final String URL_REGISTER = ROOT_URL + "registerUser.php";
    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_LOGIN_ID = ROOT_URL + "loadUserById.php";
    public static final String URL_EMAIL_CHECK = ROOT_URL + "emailExist.php";
    public static final String URL_LOAD_USER = ROOT_URL + "loadUser.php";
    public static final String URL_UPDATE = ROOT_URL + "updateUser.php";
    public static final String URL_LOAD_BY_COORDINATES = ROOT_URL + "loadUserByCoordinates.php";
    public static final String URL_LOAD_BY_CITY = ROOT_URL + "loadUserByCity.php";

    // Swipes
    public static final String URL_REGISTER_SWIPE = ROOT_URL + "registerSwipe.php";
    public static final String URL_LOAD_SWIPES = ROOT_URL + "loadSwipedList.php";
    public static final String URL_DELETE_SWIPES = ROOT_URL + "deleteSwipe.php";

    // Sympathy
    public static final String URL_REGISTER_SYMPATHY = ROOT_URL + "registerSympathy.php";
    public static final String URL_LOAD_SYMPATHIES = ROOT_URL + "loadSympathies.php";
}
