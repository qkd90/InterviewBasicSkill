//正则表达式匹配
public static Integer verifyPasswordComplexity(String password) {
        String strongRegexp =
                "^(?![\\da-z]+$)(?![\\dA-Z]+$)(?![\\d!#$%^&*.]+$)(?![a-zA-Z]+$)(?![a-z!#$%^&*.]+$)(?![A-Z!#$%^&*.]+$)[\\da-zA-z!#$%^&*" +
                        ".]{0,100}$";
        String mediumRegexp = "^(?![\\d]+$)(?![a-z]+$)(?![A-Z]+$)(?![!#$%^&*]+$)[\\da-zA-z!#$%^&*]{0,100}$";
        String weakRegexp = "^(\\d+|[a-z]+|[A-Z]+|[!#$%^&*]+)$";
        if (password.matches(strongRegexp)) {
            return 2;
        }
        if (password.matches(mediumRegexp)) {
            return 1;
        }
        if (password.matches(weakRegexp)) {
            return 0;
        }
        return 3;
    }
