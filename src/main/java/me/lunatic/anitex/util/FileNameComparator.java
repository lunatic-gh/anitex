package me.lunatic.anitex.util;

import com.google.common.io.Files;
import me.lunatic.anitex.tex.Frame;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FileNameComparator implements Comparator<Frame> {

    public FileNameComparator() {
    }

    @Override
    public int compare(Frame f, Frame f1) {
        String s = Files.getNameWithoutExtension(f.id().getPath());
        String s1 = Files.getNameWithoutExtension(f1.id().getPath());
        List<Object> parts1 = partsOf(s);
        List<Object> parts2 = partsOf(s1);
        while (!parts1.isEmpty() && !parts2.isEmpty()) {
            Object part1 = parts1.remove(0);
            Object part2 = parts2.remove(0);
            int cmp = 0;
            if (part1 instanceof Integer && part2 instanceof Integer) {
                cmp = Integer.compare((Integer) part1, (Integer) part2);
            } else if (part1 instanceof String && part2 instanceof String) {
                cmp = ((String) part1).compareTo((String) part2);
            } else {
                cmp = part1 instanceof String ? 1 : -1;
            }
            if (cmp != 0) {
                return cmp;
            }
        }
        if (parts1.isEmpty() && parts2.isEmpty()) {
            return 0;
        }
        return parts1.isEmpty() ? -1 : 1;
    }

    private List<Object> partsOf(String s) {
        List<Object> parts = new LinkedList<>();
        int pos0 = 0;
        int pos = 0;
        boolean wasDigit = false;
        while (true) {
            if (pos >= s.length()
                    || Character.isDigit(s.charAt(pos)) != wasDigit) {
                if (pos > pos0) {
                    String part = s.substring(pos0, pos);
                    parts.add(wasDigit ? Integer.valueOf(part) : part);
                    pos0 = pos;
                }
                if (pos >= s.length()) {
                    break;
                }
                wasDigit = !wasDigit;
            }
            ++pos;
        }
        return parts;
    }
}
