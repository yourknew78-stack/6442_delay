import java.util.*;
import java.nio.file.*;
/** 独立辅助核查：全状态按键矩阵、单例、XML写出内容，不计入正式异常测试。 */
public class LocalValidation {
    public static void main(String[] args) throws Exception {
        State[] states={StandState.getInstance(),LieProneState.getInstance(),RunState.getInstance(),CrawlState.getInstance(),ShootState.getInstance()};
        Key[] keys={Key.RESET,Key.UP,Key.DOWN,Key.RIGHT,Key.LEFT,Key.S,Key.L};
        // 行=当前状态，列=按键，数字对应states下标，直接来自Graph.pdf。
        int[][] expected={{0,0,1,2,0,0,0},{0,0,1,3,1,4,1},{0,2,2,2,0,2,2},
                {0,3,3,3,1,3,3},{0,4,4,4,4,4,1}};
        for(int i=0;i<states.length;i++)for(int k=0;k<keys.length;k++){
            Character c=new Character();c.setState(states[i]);states[i].handle(c,keys[k]);
            if(c.getState()!=states[expected[i][k]])throw new AssertionError(i+"/"+keys[k]);
        }
        Path file=Files.createTempFile("states-roundtrip-",".xml");
        try{
            List<String> keysText=List.of("DOWN","S","L","RESET");
            List<String> values=List.of("LieProneState","ShootState","LieProneState","StandState");
            XMLProcessor.write(file.toString(),keysText,values);
            if(!List.of(keysText,values).equals(XMLProcessor.read(file.toString())))throw new AssertionError("XML内容/顺序错误");
        }finally{Files.deleteIfExists(file);}
        System.out.println("额外验证：35个状态/按键组合、单例身份、XML内容与顺序通过。");
    }
}
