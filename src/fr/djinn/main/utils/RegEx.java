package fr.djinn.main.utils;

import java.util.regex.Pattern;

public class RegEx {

    public static final Pattern PATTERN_CODE_POSTAL = Pattern.compile("^[0-9]{5}$");
    public static final Pattern PATTERN_TELEPHONE = Pattern.compile("^[0-9]{10}$");
    public static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
}
