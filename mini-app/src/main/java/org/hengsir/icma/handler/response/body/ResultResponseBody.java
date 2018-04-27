package org.hengsir.icma.handler.response.body;

import org.hengsir.icma.entity.User;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/27 下午3:54
 */
public class ResultResponseBody {
    private List<User> match;
    private List<User> noMatch;
    private Integer matchNum;
    private Integer noMatchNum;

    public List<User> getMatch() {
        return match;
    }

    public void setMatch(List<User> match) {
        this.match = match;
    }

    public List<User> getNoMatch() {
        return noMatch;
    }

    public void setNoMatch(List<User> noMatch) {
        this.noMatch = noMatch;
    }

    public Integer getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(Integer matchNum) {
        this.matchNum = matchNum;
    }

    public Integer getNoMatchNum() {
        return noMatchNum;
    }

    public void setNoMatchNum(Integer noMatchNum) {
        this.noMatchNum = noMatchNum;
    }
}
