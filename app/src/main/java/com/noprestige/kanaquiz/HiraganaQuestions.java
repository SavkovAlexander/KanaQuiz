package com.noprestige.kanaquiz;

abstract class HiraganaQuestions
{
    private static final KanaQuestion[] KANA_SET_1 = {
            new KanaQuestion('あ', "a"),
            new KanaQuestion('い', "i"),
            new KanaQuestion('う', "u"),
            new KanaQuestion('え', "e"),
            new KanaQuestion('お', "o")};

    private static final KanaQuestion[] KANA_SET_2 = {
            new KanaQuestion('か', "ka"),
            new KanaQuestion('き', "ki"),
            new KanaQuestion('く', "ku"),
            new KanaQuestion('け', "ke"),
            new KanaQuestion('こ', "ko"),
            new KanaQuestion('が', "ga"),
            new KanaQuestion('ぎ', "gi"),
            new KanaQuestion('ぐ', "gu"),
            new KanaQuestion('げ', "ge"),
            new KanaQuestion('ご', "go")};

    private static final KanaQuestion[] KANA_SET_3 = {
            new KanaQuestion('さ', "sa"),
            new KanaQuestion('し', "shi"),
            new KanaQuestion('す', "su"),
            new KanaQuestion('せ', "se"),
            new KanaQuestion('そ', "so"),
            new KanaQuestion('ざ', "za"),
            new KanaQuestion('じ', "ji"),
            new KanaQuestion('ず', "zu"),
            new KanaQuestion('ぜ', "ze"),
            new KanaQuestion('ぞ', "zo")};

    private static final KanaQuestion[] KANA_SET_4 = {
            new KanaQuestion('た', "ta"),
            new KanaQuestion('ち', "chi"),
            new KanaQuestion('つ', "tsu"),
            new KanaQuestion('て', "te"),
            new KanaQuestion('と', "to"),
            new KanaQuestion('だ', "da"),
            new KanaQuestion('ぢ', "ji"),
            new KanaQuestion('づ', "zu"),
            new KanaQuestion('で', "de"),
            new KanaQuestion('ど', "do")};

    private static final KanaQuestion[] KANA_SET_5 = {
            new KanaQuestion('な', "na"),
            new KanaQuestion('に', "ni"),
            new KanaQuestion('ぬ', "nu"),
            new KanaQuestion('ね', "ne"),
            new KanaQuestion('の', "no")};

    private static final KanaQuestion[] KANA_SET_6 = {
            new KanaQuestion('は', "ha"),
            new KanaQuestion('ひ', "hi"),
            new KanaQuestion('ふ', "fu"),
            new KanaQuestion('へ', "he"),
            new KanaQuestion('ほ', "ho"),
            new KanaQuestion('ば', "ba"),
            new KanaQuestion('び', "bi"),
            new KanaQuestion('ぶ', "bu"),
            new KanaQuestion('べ', "be"),
            new KanaQuestion('ぼ', "bo"),
            new KanaQuestion('ぱ', "pa"),
            new KanaQuestion('ぴ', "pi"),
            new KanaQuestion('ぷ', "pu"),
            new KanaQuestion('ぺ', "pe"),
            new KanaQuestion('ぽ', "po")};

    private static final KanaQuestion[] KANA_SET_7 = {
            new KanaQuestion('ま', "ma"),
            new KanaQuestion('み', "mi"),
            new KanaQuestion('む', "mu"),
            new KanaQuestion('め', "me"),
            new KanaQuestion('も', "mo")};

    private static final KanaQuestion[] KANA_SET_8 = {
            new KanaQuestion('ら', "ra"),
            new KanaQuestion('り', "ri"),
            new KanaQuestion('る', "ru"),
            new KanaQuestion('れ', "re"),
            new KanaQuestion('ろ', "ro")};

    private static final KanaQuestion[] KANA_SET_9 = {
            new KanaQuestion('や', "ya"),
            new KanaQuestion('ゆ', "yu"),
            new KanaQuestion('よ', "yo")};

    private static final KanaQuestion[] KANA_SET_10 = {
            new KanaQuestion('わ', "wa"),
            new KanaQuestion('を', "wo"),
            new KanaQuestion('ん', "n")};

    static KanaQuestionBank getQuestionBank()
    {
        KanaQuestionBank questionBank = new KanaQuestionBank();

        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_1))
            questionBank.addQuestions(KANA_SET_1);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_2))
            questionBank.addQuestions(KANA_SET_2);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_3))
            questionBank.addQuestions(KANA_SET_3);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_4))
            questionBank.addQuestions(KANA_SET_4);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_5))
            questionBank.addQuestions(KANA_SET_5);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_6))
            questionBank.addQuestions(KANA_SET_6);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_7))
            questionBank.addQuestions(KANA_SET_7);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_8))
            questionBank.addQuestions(KANA_SET_8);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_9))
            questionBank.addQuestions(KANA_SET_9);
        if (OptionsControl.getBoolean(OptionsControl.CODE_HIRAGANA_10))
            questionBank.addQuestions(KANA_SET_10);

        return questionBank;
    }
}
