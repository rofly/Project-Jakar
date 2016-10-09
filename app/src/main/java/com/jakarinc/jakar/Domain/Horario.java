package com.jakarinc.jakar.Domain;


public class Horario {
    private String userId;
    private long horasTimeStamp;
    private int token;

    public Horario(String conteudo) {
        String[] vars = conteudo.split("\0");
        userId = vars[0];
        horasTimeStamp = Long.valueOf(vars[1]);
        token = Integer.valueOf(vars[2]);
    }

    public Horario(int token, long horas, String userId) {
        this.token = token;
        this.horasTimeStamp = horas;
        this.userId = userId;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getHorasTimeStamp() {
        return horasTimeStamp;
    }

    public void setHorasTimeStamp(long horasTimeStamp) {
        this.horasTimeStamp = horasTimeStamp;
    }

    public String ToString() {
        return (userId + '\0' + horasTimeStamp + '\0' + token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Horario horario = (Horario) o;

        if (horasTimeStamp != horario.horasTimeStamp) return false;
        if (token != horario.token) return false;
        return userId != null ? userId.equals(horario.userId) : horario.userId == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (int) (horasTimeStamp ^ (horasTimeStamp >>> 32));
        result = 31 * result + token;
        return result;
    }
}
