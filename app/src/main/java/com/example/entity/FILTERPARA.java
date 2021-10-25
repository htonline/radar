package com.example.entity;

public class FILTERPARA {
    float	m_high_f;//高
    float	m_low_f;//低
    int		m_mode;//滤波模式：

    @Override
    public String toString() {
        return "FILTERPARA{" +
                "m_high_f=" + m_high_f +
                ", m_low_f=" + m_low_f +
                ", m_mode=" + m_mode +
                '}';
    }

    public float getM_high_f() {
        return m_high_f;
    }

    public void setM_high_f(float m_high_f) {
        this.m_high_f = m_high_f;
    }

    public float getM_low_f() {
        return m_low_f;
    }

    public void setM_low_f(float m_low_f) {
        this.m_low_f = m_low_f;
    }

    public int getM_mode() {
        return m_mode;
    }

    public void setM_mode(int m_mode) {
        this.m_mode = m_mode;
    }
}
