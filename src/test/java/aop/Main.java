package aop;

public class Main {
    public static void main(String[] args) {
        UserMapper build=new MapperProxy().bulid(UserMapper.class);

        System.out.println(build.list());
    }
}
