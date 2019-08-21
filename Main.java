package com.maksimtymkovskiy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get("\\src\\com\\maksimtymkovskiy\\input.txt");

        List<String> list = null;
        try {
            list = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int n = Integer.parseInt(list.get(1).split(" ")[0]);
        int m = Integer.parseInt(list.get(1).split(" ")[1]);
        int k = Integer.parseInt(list.get(1).split(" ")[2]);

        List<Integer> array = new ArrayList<>(n);
        System.out.println(list.get(0));
        System.out.println(n + " " + m + " " + k);
        int c = 0;
        for (String item : list.get(2).split(" ")
        ) {
            array.add(Integer.parseInt(item));
            System.out.print(array.get(c) + " ");
            c++;
        }

        List<Integer> answer = answerList(array, new ArrayList<>(k), m, k);

        try {
            Writer writer = new FileWriter(path.toFile(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("\n\routput : \n");
            System.out.println("\noutput : ");
            for (int i = 0; i < answer.size(); i++) {
                int index = array.indexOf(answer.get(i));
                System.out.print(index + " ");
                array.set(index, 0);
                bw.write(index + " ");
            }
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<Integer> answerList(List<Integer> array, List<Integer> answer, int m, int k) {
        List<Integer> sorted = new ArrayList<>(array);
        Collections.sort(sorted);

        int count = 0;
        int rest = m;
        for (int j = sorted.size() - 1; j >= 0; j--) {
            if (!(rest % sorted.get(j) == 0)) {
                continue;
            }
            answer.add(sorted.get(j));
            count++;
            rest = rest / sorted.get(j);
            if (count == k && rest == 1) {
                return answer;
            }

            for (int i = j - 1; i >= 0; i--) {

                if (k == 2) {
                    if (sorted.subList(0, i).contains(rest)) {
                        answer.add(rest);
                        count++;
                        return answer;
                    } else {
                        answer.clear();
                        count = 0;
                        rest = m;
                        break;
                    }
                }

                if (!(rest % sorted.get(i) == 0)) {
                    continue;
                }

                answer.add(sorted.get(i));
                count++;
                rest = rest / sorted.get(i);

                if (k - count == 1) {
                    if (sorted.subList(0, i).contains(rest)) {
                        answer.add(rest);
                        count++;
                        return answer;
                    } else {
                        answer.clear();
                        count = 0;
                        rest = m;
                        break;
                    }
                }
            }
        }
        return answer;
    }
}
