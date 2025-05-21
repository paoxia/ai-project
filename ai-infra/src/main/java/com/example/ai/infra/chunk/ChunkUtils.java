package com.example.ai.infra.chunk;

import java.util.ArrayList;
import java.util.List;


/**
 * 切分代码
 */
public class ChunkUtils {

    /**
     * 根据句子chunk
     *
     * @param text   文档
     * @param maxLen 最长大小
     * @return 切分的chunk
     */
    public static List<String> chunkBySentence(String text, int maxLen) {
        String[] sentences = text.split("(?<=[。！？；])"); // 中文标点切割
        List<String> chunks = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (String sentence : sentences) {
            if (current.length() + sentence.length() > maxLen) {
                chunks.add(current.toString().trim());
                current.setLength(0);
            }
            current.append(sentence);
        }

        if (!current.isEmpty()) {
            chunks.add(current.toString().trim());
        }

        return chunks;
    }

    /**
     * 滑动窗口 chunk
     *
     * @param text      文章
     * @param chunkSize chunk大小
     * @param overlap   重叠部分
     * @return 切分的chunk
     */
    public static List<String> slidingWindowChunk(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        int i = 0;
        while (i < text.length()) {
            int end = Math.min(i + chunkSize, text.length());
            chunks.add(text.substring(i, end));
            i += (chunkSize - overlap);
        }
        return chunks;
    }

}
