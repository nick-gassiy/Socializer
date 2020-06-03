package com.android.badoonmysql.Helpers;

import android.content.Intent;
import android.text.TextUtils;
import com.android.badoonmysql.Cards.Swipe;
import com.android.badoonmysql.Users.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Intent goToNextActivity(User user, Intent intent) {
        intent.putExtra("user", user);
        return intent;
    }

    public static boolean isFieldNotValid(String value, String symbols) {
        Pattern special = Pattern.compile (symbols);
        Matcher hasSpecial = special.matcher(value);
        return !(TextUtils.isEmpty(value) || hasSpecial.find());
    }

    public static boolean isFieldNotValid(String value, String symbols1, String symbols2) {
        Pattern digit = Pattern.compile(symbols1);
        Pattern special = Pattern.compile (symbols2);
        Matcher hasDigit = digit.matcher(value);
        Matcher hasSpecial = special.matcher(value);
        return (TextUtils.isEmpty(value) || hasDigit.find() || hasSpecial.find());
    }

    public static void fillUsersFields(User user, JSONObject obj) throws JSONException {
        user.setID(obj.getInt("id") + "");
        user.setName(obj.getString("name"));
        user.setMail(obj.getString("mail"));
        user.setPassword(obj.getString("password"));
        user.setGender(obj.getString("gender"));
        user.setBio(obj.getString("bio"));
        user.setCity(obj.getString("city"));
        user.setBirthday(obj.getString("birthday"));
        user.setMainPhoto(obj.getString("main_photo_path"));
        user.setLatitude(Double.parseDouble(obj.getString("latitude")));
        user.setLongitude(Double.parseDouble(obj.getString("longitude")));
    }

    public static void fillSwipesFields(Swipe swipe, JSONObject obj) throws JSONException {
        swipe.setId(obj.getInt("id"));
        swipe.setWasVisitedBy(obj.getInt("was_visited_by"));
        swipe.setWhoWasVisited(obj.getInt("who_was_visited"));
        swipe.setLeftSwipe(Boolean.parseBoolean(obj.getString("is_left_swipe")));
    }

    public static String translateToEng(String src) {
        String [] f = {"А","Б","В","Г","Д","Е","Ё", "Ж", "З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х","Ч", "Ц","Ш", "Щ", "Э","Ю", "Я", "Ы","Ъ", "Ь", "а","б","в","г","д","е","ё", "ж", "з","и","й","к","л","м","н","о","п","р","с","т","у","ф","х","ч", "ц","ш", "щ", "э","ю", "я", "ы","ъ","ь"};
        String [] t = {"A","B","V","G","D","E","Jo","Zh","Z","I","J","K","L","M","N","O","P","R","S","T","U","F","H","Ch","C","Sh","Csh","E","Ju","Ja","Y","`", "'", "a","b","v","g","d","e","jo","zh","z","i","j","k","l","m","n","o","p","r","s","t","u","f","h","ch","c","sh","csh","e","ju","ja","y","`","'"};

        String res = "";

        for (int i = 0; i < src.length(); ++i) {
            String add = src.substring(i, i + 1);
            for (int j = 0; j < f.length; j++) {
                if (f[j].equals(add)) {
                    add = t[j];
                    break;
                }
            }
            res += add;
        }

        if (src.equals(res))
            return res;
        else
            return res + "_rus_tr";
    }

    public static String translateToRus(String src) {
        String res = src;

        if (src.contains("_rus_tr")) {

            res = "";

            String[] t = {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ч", "Ц", "Ш", "Щ", "Э", "Ю", "Я", "Ы", "Ъ", "Ь", "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ч", "ц", "ш", "щ", "э", "ю", "я", "ы", "ъ", "ь"};
            String[] f = {"A", "B", "V", "G", "D", "E", "Jo", "Zh", "Z", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ch", "C", "Sh", "Csh", "E", "Ju", "Ja", "Y", "`", "'", "a", "b", "v", "g", "d", "e", "jo", "zh", "z", "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ch", "c", "sh", "csh", "e", "ju", "ja", "y", "`", "'"};

            for (int i = 0; i < src.length(); ++i) {
                String add = src.substring(i, i + 1);
                for (int j = 0; j < f.length; j++) {
                    if (f[j].equals(add)) {
                        add = t[j];
                        break;
                    }
                }
                res += add;
            }
        }
        return res;
    }
}
