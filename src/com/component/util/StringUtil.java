package com.component.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串操作工具类
 * UserBase: Administrator
 * Date: 2007-1-4
 * Time: 16:30:57
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    /**
     * 在源字串后追加字符串，用‘，’为间隔
     *
     * @param s   源字串
     * @param add 追加的字符串
     * @return 追加后的新字符串
     */
    public static String add(String s, String add) {
        return add(s, add, StringPool.COMMA);
    }

    /**
     * 在源字串后追加字符串，用指定间隔符间隔
     *
     * @param s         源字串
     * @param add       追加的字符串
     * @param delimiter 间隔符
     * @return 追加后的新字符串
     */
    public static String add(String s, String add, String delimiter) {
        return add(s, add, delimiter, false);
    }

    /**
     * 在源字串后追加字符串，用指定间隔符间隔 ；
     * 若规定了不允许重复，则仅在追加的字串不包含在源字串的情况瞎，才进行追加
     *
     * @param s               源字串
     * @param add             追加的字符串
     * @param delimiter       间隔符
     * @param allowDuplicates 是否允许重复
     * @return 追加后的新字符串
     */
    public static String add(
            String s, String add, String delimiter, boolean allowDuplicates) {

        if ((add == null) || (delimiter == null)) {
            return null;
        }

        if (s == null) {
            s = StringPool.BLANK;
        }

        if (allowDuplicates || !contains(s, add, delimiter)) {
            if (Validator.isNull(s) || s.endsWith(delimiter)) {
                s += add + delimiter;
            } else {
                s += delimiter + add + delimiter;
            }
        }

        return s;
    }

    /**
     * 判断是否源字串中包含了给定子字串
     *
     * @param s    源字串
     * @param text 子字串
     * @return 若包含，则返回true；否则返回false
     */
    public static boolean contains(String s, String text) {
        return contains(s, text, StringPool.COMMA);
    }

    /**
     * 判断是否源字串中包含了给定子字串
     *
     * @param s         源字串
     * @param text      子字串
     * @param delimiter 字串分隔符
     * @return 若包含，则返回true；否则返回false
     */
    public static boolean contains(String s, String text, String delimiter) {
        if ((s == null) || (text == null) || (delimiter == null)) {
            return false;
        }

        if (!s.endsWith(delimiter)) {
            s += delimiter;
        }

        int pos = s.indexOf(delimiter + text + delimiter);

        if (pos == -1) {
            if (s.startsWith(text + delimiter)) {
                return true;
            }

            return false;
        }

        return true;
    }

    /**
     * 计算源字串中，包含了几个给定子字串
     *
     * @param s    源字串
     * @param text 子字串
     * @return 子字串在源字串中的个数
     */
    public static int count(String s, String text) {
        if ((s == null) || (text == null)) {
            return 0;
        }

        int count = 0;

        int pos = s.indexOf(text);

        while (pos != -1) {
            pos = s.indexOf(text, pos + text.length());
            count++;
        }

        return count;
    }

    /**
     * 判断源字串是否以指定字符结尾
     *
     * @param s   源字串
     * @param end 指定字符
     * @return 若源字串以指定字符结尾，则返回true；否则返回false
     */
    public static boolean endsWith(String s, char end) {
        return endsWith(s, (new Character(end)).toString());
    }

    /**
     * 判断源字串是否以指定字符串结尾
     *
     * @param s   源字串
     * @param end 指定字符串
     * @return 若源字串以指定字符串结尾，则返回true；否则返回false
     */
    public static boolean endsWith(String s, String end) {
        if ((s == null) || (end == null)) {
            return false;
        }

        if (end.length() > s.length()) {
            return false;
        }

        String temp = s.substring(s.length() - end.length(), s.length());

        return temp.equalsIgnoreCase(end);
    }

    /**
     * 提取源字串中的字符（中英文字母）
     *
     * @param s 源字串
     * @return 提取字符组成的新字串
     */
    public static String extractChars(String s) {
        if (s == null) {
            return "";
        }

        char[] c = s.toCharArray();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < c.length; i++) {
            if (Validator.isLetter(c[i])) {
                sb.append(c[i]);
            }
        }

        return sb.toString();
    }

    /**
     * 提取源字串中的数字
     *
     * @param s 源字串
     * @return 提取出的数字组成的新字串
     */
    public static String extractDigits(String s) {
        if (s == null) {
            return "";
        }

        char[] c = s.toCharArray();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < c.length; i++) {
            if (Validator.isDigit(c[i])) {
                sb.append(c[i]);
            }
        }

        return sb.toString();
    }

    /**
     * 提取源字串中第一个子串（以指定分隔符为分隔）
     *
     * @param s         源字串
     * @param delimiter 指定分隔符
     * @return 提取出的第一个子串
     */
    public static String extractFirst(String s, String delimiter) {
        if (s == null) {
            return null;
        } else {
            String[] array = split(s, delimiter);

            if (array.length > 0) {
                return array[0];
            } else {
                return null;
            }
        }
    }

    /**
     * 提取源字串中最后一个子串（以指定分隔符为分隔）
     *
     * @param s         源字串
     * @param delimiter 指定分隔符
     * @return 提取出的最后一个子串
     */
    public static String extractLast(String s, String delimiter) {
        if (s == null) {
            return null;
        } else {
            String[] array = split(s, delimiter);

            if (array.length > 0) {
                return array[array.length - 1];
            } else {
                return null;
            }
        }
    }

    /**
     * 将源字串转换为全部小写
     *
     * @param s 源字串
     * @return 转换为小写后的新字串
     */
    public static String lowerCase(String s) {
        if (s == null) {
            return null;
        } else {
            return s.toLowerCase();
        }
    }

    /**
     * 将字符串list用逗号拼接转换为字符串
     *
     * @param list 源字符串list
     * @return 拼接后的新字串
     */
    public static String merge(List list) {
        return merge(list, StringPool.COMMA);
    }

    /**
     * 将字符串list用分隔符拼接转换为字符串
     *
     * @param list      源字符串list
     * @param delimiter 分隔符
     * @return 拼接后的新字串
     */
    public static String merge(List list, String delimiter) {
        return merge((String[]) list.toArray(new String[0]), delimiter);
    }

    /**
     * 将字符串数组用逗号拼接转换为字符串
     *
     * @param array 源字符串数组
     * @return 拼接后的新字串
     */
    public static String merge(String[] array) {
        return merge(array, StringPool.COMMA);
    }

    /**
     * 将字符串数组用分隔符拼接转换为字符串
     *
     * @param array     源字符串数组
     * @param delimiter 分隔符
     * @return 拼接后的新字串
     */
    public static String merge(String[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i].trim());

            if ((i + 1) != array.length) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    /**
     * 随机重组源字串中的字符
     *
     * @param s 源字串
     * @return 随机重组后的新字串
     */
    public static String randomize(String s) {
        return Randomizer.getInstance().randomize(s);
    }


    /**
     * 读取指定类装载路径下，指定资源文件内容
     *
     * @param classLoader 类装载器
     * @param name        资源文件
     * @return 资源文件内容（字符串形式）
     * @throws IOException
     */
    public static String read(ClassLoader classLoader, String name)
            throws IOException {

        return read(classLoader.getResourceAsStream(name));
    }

    /**
     * 读取给定输入流内容
     *
     * @param is 输入流
     * @return 输入流内容（字符串形式）
     * @throws IOException
     */
    public static String read(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuffer sb = new StringBuffer();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line).append('\n');
        }

        br.close();

        return sb.toString().trim();
    }


    /**
     * 从源字串中拆除指定字串（以逗号为识别分隔符）
     *
     * @param s      源字串
     * @param remove 要摘除的指定字串
     * @return 拆除子串后的新串
     */
    public static String remove(String s, String remove) {
        return remove(s, remove, StringPool.COMMA);
    }


    /**
     * 从源字串中拆除指定字串（以指定分隔符为识别分隔符）
     *
     * @param s         源字串
     * @param remove    要摘除的指定字串
     * @param delimiter 指定分隔符
     * @return 拆除子串后的新串
     */
    public static String remove(String s, String remove, String delimiter) {
        if ((s == null) || (remove == null) || (delimiter == null)) {
            return null;
        }

        if (Validator.isNotNull(s) && !s.endsWith(delimiter)) {
            s += delimiter;
        }

        while (contains(s, remove, delimiter)) {
            int pos = s.indexOf(delimiter + remove + delimiter);

            if (pos == -1) {
                if (s.startsWith(remove + delimiter)) {
                    int x = remove.length() + delimiter.length();
                    int y = s.length();

                    s = s.substring(x, y);
                }
            } else {
                int x = pos + remove.length() + delimiter.length();
                int y = s.length();

                s = s.substring(0, pos) + s.substring(x, y);
            }
        }

        return s;
    }


    /**
     * 将源字串中的指定字符，用新字符替代
     *
     * @param s      源字串
     * @param oldSub 指定要被替代的字符
     * @param newSub 新字符
     * @return 替换后的新串
     */
    public static String replace(String s, char oldSub, char newSub) {
        return replace(s, oldSub, Character.toString(newSub));
    }

    /**
     * 将源字串中的指定字符，用新字符串替代
     *
     * @param s      源字串
     * @param oldSub 指定要被替代的字符
     * @param newSub 新字符串
     * @return 替换后的新串
     */
    public static String replace(String s, char oldSub, String newSub) {
        if ((s == null) || (newSub == null)) {
            return null;
        }

        char[] c = s.toCharArray();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == oldSub) {
                sb.append(newSub);
            } else {
                sb.append(c[i]);
            }
        }

        return sb.toString();
    }


    /**
     * 将源字串中的指定字符串，用新字符串替代
     *
     * @param s      源字串
     * @param oldSub 指定要被替代的字符串
     * @param newSub 新字符串
     * @return 替换后的新字符串
     */
    public static String replace(String s, String oldSub, String newSub) {
        if ((s == null) || (oldSub == null) || (newSub == null)) {
            return null;
        }

        int y = s.indexOf(oldSub);

        if (y >= 0) {
            StringBuffer sb = new StringBuffer();

            int length = oldSub.length();
            int x = 0;

            while (x <= y) {
                sb.append(s.substring(x, y));
                sb.append(newSub);
                x = y + length;
                y = s.indexOf(oldSub, x);
            }

            sb.append(s.substring(x));

            return sb.toString();
        } else {
            return s;
        }
    }

    /**
     * 将源字符串中一组指定字串，用一组新串替换
     *
     * @param s       源字符串
     * @param oldSubs 要被替换的一组旧子字串
     * @param newSubs 一组新字串
     * @return 替换后的新字符串
     */
    public static String replace(String s, String[] oldSubs, String[] newSubs) {
        if ((s == null) || (oldSubs == null) || (newSubs == null)) {
            return null;
        }

        if (oldSubs.length != newSubs.length) {
            return s;
        }

        for (int i = 0; i < oldSubs.length; i++) {
            s = replace(s, oldSubs[i], newSubs[i]);
        }

        return s;
    }


    /**
     * 将源字串倒序，形成新字串
     *
     * @param s 源字串
     * @return 倒序后的新字串
     */
    public static String reverse(String s) {
        if (s == null) {
            return null;
        }

        char[] c = s.toCharArray();
        char[] reverse = new char[c.length];

        for (int i = 0; i < c.length; i++) {
            reverse[i] = c[c.length - i - 1];
        }

        return new String(reverse);
    }

    /**
     * 取源字串前20个字符，剩余用“…”显示
     *
     * @param s 源字串
     * @return 处理后的新串
     */
    public static String shorten(String s) {
        return shorten(s, 20);
    }

    /**
     * 取源字串指定长度的前几个字符，剩余用“…”显示
     *
     * @param s      源字串
     * @param length 显示的前若个个字符个数
     * @return 处理后的新字串
     */
    public static String shorten(String s, int length) {
        return shorten(s, length, "...");
    }

    /**
     * 取源字串前20个字符，剩余用指定省略字符显示
     *
     * @param s      源字串
     * @param suffix 指定省略字符
     * @return 处理后的新字串
     */
    public static String shorten(String s, String suffix) {
        return shorten(s, 20, suffix);
    }

    /**
     * 取源字串指定长度的前几个字符，剩余用指定省略字符显示
     *
     * @param s      源字串
     * @param length 显示的前若个个字符个数
     * @param suffix 指定省略字符
     * @return 处理后的新字串
     */
    public static String shorten(String s, int length, String suffix) {
        if (s == null || suffix == null) {
            return null;
        }

        if (s.length() > length) {
            for (int j = length; j >= 0; j--) {
                if (Character.isWhitespace(s.charAt(j))) {
                    length = j;

                    break;
                }
            }

            s = s.substring(0, length) + suffix;
        }

        return s;
    }

    /**
     * 将源字串用逗号分隔，取得分隔后的字符数组
     *
     * @param s 源字串
     * @return 分隔后的字符数组
     */
    public static String[] split(String s) {
        return split(s, StringPool.COMMA);
    }

    /**
     * 将源字串用指定分隔符分隔，取得分隔后的字符数组
     *
     * @param s         源字串
     * @param delimiter 指定分隔符
     * @return 分隔后的字符数组
     */
    public static String[] split(String s, String delimiter) {
        if (s == null || delimiter == null) {
            return new String[0];
        }

        s = s.trim();

        if (!s.endsWith(delimiter)) {
            s += delimiter;
        }

        if (s.equals(delimiter)) {
            return new String[0];
        }

        List nodeValues = new ArrayList();

        if (delimiter.equals("\n") || delimiter.equals("\r")) {
            try {
                BufferedReader br = new BufferedReader(new StringReader(s));

                String line;

                while ((line = br.readLine()) != null) {
                    nodeValues.add(line);
                }

                br.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            int offset = 0;
            int pos = s.indexOf(delimiter, offset);

            while (pos != -1) {
                nodeValues.add(s.substring(offset, pos));

                offset = pos + delimiter.length();
                pos = s.indexOf(delimiter, offset);
            }
        }

        return (String[]) nodeValues.toArray(new String[0]);
    }

    /**
     * 将源字串用指定分隔符分隔，分隔后转换boolean型数组
     *
     * @param s         源字符串
     * @param delimiter 指定分隔符
     * @param x         boolean型元素默认值
     * @return 分隔后的boolean数组
     */
    public static boolean[] split(String s, String delimiter, boolean x) {
        String[] array = split(s, delimiter);
        boolean[] newArray = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            boolean value = x;

            try {
                value = Boolean.valueOf(array[i]).booleanValue();
            }
            catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * 将源字串用指定分隔符分隔，分隔后转换为double型数组
     *
     * @param s         源字符串
     * @param delimiter 指定分隔符
     * @param x         double型元素默认值
     * @return 分隔后的double数组
     */
    public static double[] split(String s, String delimiter, double x) {
        String[] array = split(s, delimiter);
        double[] newArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            double value = x;

            try {
                value = Double.parseDouble(array[i]);
            }
            catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * 将源字串用指定分隔符分隔，分隔后转换为float型数组
     *
     * @param s         源字符串
     * @param delimiter 指定分隔符
     * @param x         float型元素默认值
     * @return 分隔后的float数组
     */
    public static float[] split(String s, String delimiter, float x) {
        String[] array = split(s, delimiter);
        float[] newArray = new float[array.length];

        for (int i = 0; i < array.length; i++) {
            float value = x;

            try {
                value = Float.parseFloat(array[i]);
            }
            catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * 将源字串用指定分隔符分隔，分隔后转换为int型数组
     *
     * @param s         源字符串
     * @param delimiter 指定分隔符
     * @param x         int型元素默认值
     * @return 分隔后的int数组
     */
    public static int[] split(String s, String delimiter, int x) {
        String[] array = split(s, delimiter);
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            int value = x;

            try {
                value = Integer.parseInt(array[i]);
            }
            catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * 将源字串用指定分隔符分隔，分隔后转换long型数组
     *
     * @param s         源字符串
     * @param delimiter 指定分隔符
     * @param x         long型元素默认值
     * @return 分隔后的long数组
     */
    public static long[] split(String s, String delimiter, long x) {
        String[] array = split(s, delimiter);
        long[] newArray = new long[array.length];

        for (int i = 0; i < array.length; i++) {
            long value = x;

            try {
                value = Long.parseLong(array[i]);
            }
            catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * 将源字串用指定分隔符分隔，分隔后转换short型数组
     *
     * @param s         源字符串
     * @param delimiter 指定分隔符
     * @param x         short型元素默认值
     * @return 分隔后的short数组
     */
    public static short[] split(String s, String delimiter, short x) {
        String[] array = split(s, delimiter);
        short[] newArray = new short[array.length];

        for (int i = 0; i < array.length; i++) {
            short value = x;

            try {
                value = Short.parseShort(array[i]);
            }
            catch (Exception e) {
            }

            newArray[i] = value;
        }

        return newArray;
    }

    /**
     * 判断源字串是否以指定字符开头
     *
     * @param s     源字串
     * @param begin 指定字符
     * @return 若源字串以指定字符开头，则返回true；否则返回false
     */
    public static boolean startsWith(String s, char begin) {
        return startsWith(s, (new Character(begin)).toString());
    }

    /**
     * 判断源字串是否以指定子字串开头
     *
     * @param s     源字串
     * @param start 指定子字串
     * @return 若源字串以指定子字串开头，则返回true；否则返回false
     */
    public static boolean startsWith(String s, String start) {
        if ((s == null) || (start == null)) {
            return false;
        }

        if (start.length() > s.length()) {
            return false;
        }

        String temp = s.substring(0, start.length());

        if (temp.equalsIgnoreCase(start)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 去除源字串开头的空格
     *
     * @param s 源字串
     * @return 处理后的新字串
     */
    public static String trimLeading(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return s.substring(i, s.length());
            }
        }

        return StringPool.BLANK;
    }

    /**
     * 去除源字串尾部的空格
     *
     * @param s 源字串
     * @return 处理后的新字串
     */
    public static String trimTrailing(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return s.substring(0, i + 1);
            }
        }

        return StringPool.BLANK;
    }

    /**
     * 去除源字串中全部的空格
     *
     * @param s 源字串
     * @return 处理后的新字串
     */
    public static String trimWhole(String s) {
        //清除s末尾的空格
        s = s.trim();
        char[] exprArray = new char[s.length()];
        //将s中的元素复制到数组exprArray中
        for (int i = s.length() - 1; i >= 0; i--)
            exprArray[i] = s.charAt(i);
        //逐个将空格清除
        for (int i = s.length() - 1; i >= 0; i--) {
            int j;
            if (exprArray[i] == ' ') {
                j = i;
                while (j < exprArray.length - 1) {
                    exprArray[j] = exprArray[j + 1];
                    j++;
                }
                exprArray[exprArray.length - 1] = ' ';
            }
        }
        //将数组形式转换成StringBuffer形式
        StringBuffer exprStrBuf = new StringBuffer("");
        for (int i = 0; i < exprArray.length; i++) {
            exprStrBuf.insert(i, exprArray[i]);
        }
        //将StringBuffer形式转换成String形式
        s = exprStrBuf.toString().trim();

        return s;
    }

    /**
     * 将源字串全部转换为大写
     *
     * @param s 源字串
     * @return 转换为大写后的新字串
     */
    public static String upperCase(String s) {
        if (s == null) {
            return null;
        } else {
            return s.toUpperCase();
        }
    }

    /**
     * 将源字串文本按指定长度80个字符折行（每80个字符插入一个回车符）
     *
     * @param text 源字串
     * @return 处理后的新字串
     */
    public static String wrap(String text) {
        return wrap(text, 80, "\n");
    }

    /**
     * 将源字串文本按指定长度的字符插入行分隔符
     *
     * @param text          源字串
     * @param width         指定长度
     * @param lineSeparator 行分隔符
     * @return 处理后的字串
     */
    public static String wrap(String text, int width, String lineSeparator) {
        if (text == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new StringReader(text));

            String s = StringPool.BLANK;

            while ((s = br.readLine()) != null) {
                if (s.length() == 0) {
                    sb.append(lineSeparator);
                } else {
                    String[] tokens = s.split(StringPool.SPACE);
                    boolean firstWord = true;
                    int curLineLength = 0;

                    for (int i = 0; i < tokens.length; i++) {
                        if (!firstWord) {
                            sb.append(StringPool.SPACE);
                            curLineLength++;
                        }

                        if (firstWord) {
                            sb.append(lineSeparator);
                        }

                        sb.append(tokens[i]);

                        curLineLength += tokens[i].length();

                        if (curLineLength >= width) {
                            firstWord = true;
                            curLineLength = 0;
                        } else {
                            firstWord = false;
                        }
                    }
                }
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 取得指定域内的子串
     * @param string  原字符串
     * @param delimiter 域分隔符
     * @param index 要取的第几个域（从1开始）
     * @return 子串
     * @throws Exception
     */
    public static String subStringByDomain(String string, String delimiter, int index) throws Exception {
        if(index<1){
            throw new Exception("index must beyond 0");
        }
        String[] sub = split(string, delimiter);
        return sub[index-1];
    }

    /**
     * 取得按照指定第几位分隔符分隔成的子串数组
     * @param string 原字符串
     * @param delimiter  分隔副
     * @param index  第几位分隔符
     * @return 分隔成的子串数组
     * @throws Exception
     */
    public static String[] splitByIndex(String string, String delimiter, int index) throws Exception {
        String[] result = new String[2];
        String[] sub = split(string, delimiter);
        if (sub.length < index) {
            throw new Exception("index beyond the limit!");
        }
        StringBuffer subStr = new StringBuffer();
        for (int i = 0; i < index; i++) {
            if (i > 0) {
                subStr.append(delimiter);
            }
            subStr.append(sub[i]);
        }
        result[0] = subStr.toString();
        if (sub.length > index) {
            subStr = new StringBuffer();
            for (int i = index; i < sub.length; i++) {
                if (i > index) {
                    subStr.append(delimiter);
                }
                subStr.append(sub[i]);
            }
            result[1] = subStr.toString();
        }
        return result;
    }

    public static void main(String[] args) {
        // System.out.println(StringUtil.count("中国银行北京银银银监会","银"));
        try {
            /*String treeCode = ".00122.06001.06022.06033.06044.06055.";
            String sub = StringUtil.extractLast(treeCode,".");
            System.out.println("---"+sub);*/


            String treeCode = ".00122.06001.06022.06033.06044.06055.";
            String[] sub = StringUtil.splitByIndex(treeCode,".", 5);
            System.out.println("---"+sub[0]);
            System.out.println("==="+sub[1]);
            System.out.println(treeCode.indexOf(".",2));
            //System.out.println(StringUtil.subStringByDomain(treeCode,".",0));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
