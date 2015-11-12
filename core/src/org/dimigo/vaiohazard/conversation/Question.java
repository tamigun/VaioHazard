package org.dimigo.vaiohazard.conversation;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.dimigo.library.DialogGenerater;
import org.dimigo.vaiohazard.Object.PixelizedDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juwoong on 15. 11. 12..
 */
public class Question {
    private List<Question> nodes;
    private String question;
    private String requiredAnswer;
    private int level;
    private DialogGenerater generater;
    private Stage stage;
    private boolean isFinal;

    public Question(String question, String requiredAnswer) {
        this.nodes = new ArrayList<Question>();
        this.question = question;
        this.generater = new DialogGenerater();
        this.isFinal = false;
        this.requiredAnswer = requiredAnswer;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        for(Question q : nodes) {
            q.setStage(stage);
        }
    }

    public void insertQuestion(Question q) {
        nodes.add(q);
    }

    public String getQuestion() { return this.question; }
    public String getRequiredAnswer() { return this.requiredAnswer; }
    public void setRequiredAnswer(String requiredAnswer) { this.requiredAnswer = requiredAnswer; }

    public void ask() {
        PixelizedDialog dialog = generater.getDialog(question);

        for(Question q : nodes) {
            System.out.println(q.toString());
            dialog.button(q.getRequiredAnswer(), q, generater.getStyle());
        }

        if(nodes.size() <= 0 ) {
            dialog.button("대화 끝마치기.", "end", generater.getStyle());
        }

        dialog.show(stage);
    }

    @Override
    public String toString() {
        return "Question{" +
                "nodes=" + nodes +
                ", question='" + question + '\'' +
                ", requiredAnswer='" + requiredAnswer + '\'' +
                ", level=" + level +
                ", generater=" + generater +
                ", stage=" + stage +
                ", isFinal=" + isFinal +
                '}';
    }
}