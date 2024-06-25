package View;

public enum Regexes {
    showMenuName("show menu name"),
    userRegister("user create -u (?<username>[a-zA-Z0-9]+) -p (?<password>(.+)) (?<passConfirm>(.+)) -email (?<email>(.+)) -n (?<nickName>[a-zA-Z0-9]+)"),
    QE("question pick -q (?<QENumber>[a-zA-Z0-9]+) -a (?<answer>[a-zA-Z0-9]+) -c (?<answerConfirm>[a-zA-Z0-9]+)"),
    userLogin("user login -u (?<username>[a-zA-Z0-9]+) -p (?<password>(.+))"),
    forgetPassword("Forgot my Password -u (?<username>[a-zA-Z0-9]+)"),
    showProfileInfo("Show information"),
    changeUsername("Profile change -u (?<username>[a-zA-Z0-9]+)"),
    changeNickname("Profile change -n (?<nickname>[a-zA-Z0-9]+)"),
    changePassword("Profile change password -o (?<oldPass>(.+)) -n (?<newPass>(.+))"),
    changeEmail("Profile change -e (?<email>(.+))");
    public final String pattern;

    Regexes(String pattern) {
        this.pattern = pattern;
    }
}
