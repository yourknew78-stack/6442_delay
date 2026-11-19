import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.lang.reflect.*;
/** 验证“测试答案是否合理”，不是要求13个故意不同的实现全部变绿。 */
public class LocalValidation {
    private static class ReferenceHashingBox extends HashingBox {
        @Override public int hash(Thing t) throws ComponentOutOfRangeException {
            if(t.getSize()<1||t.getSize()>31)throw new ComponentOutOfRangeException();
            return ExpectedCalculator.expected(t.getMaterial(),t.getSize(),t.getShape(),t.getName());
        }
        int hashSize(int s){throw new UnsupportedOperationException();}
        int hashName(String s,int p){throw new UnsupportedOperationException();}
        int hashMaterialShape(Material m,Shape s){throw new UnsupportedOperationException();}
    }
    public static void main(String[] args) throws Exception {
        Q2Test test=new Q2Test();test.hashingBox=new ReferenceHashingBox();
        int count=0;
        for(Method m:Q2Test.class.getDeclaredMethods()){
            Test annotation=m.getAnnotation(Test.class);if(annotation==null)continue;
            Throwable thrown=null;
            try{m.invoke(test);}catch(InvocationTargetException e){thrown=e.getCause();}
            if(annotation.expected()==Test.None.class){
                if(thrown!=null)throw new AssertionError("参考实现应通过："+m.getName(),thrown);
            }else if(thrown==null||!annotation.expected().isInstance(thrown))
                throw new AssertionError("异常测试不正确："+m.getName(),thrown);
            count++;
        }
        System.out.println("独立规范参考实现通过全部 "+count+" 个测试方法。");
        Result result=JUnitCore.runClasses(Q2Test.class);
        boolean[] failed=new boolean[13];
        for(Failure f:result.getFailures()){
            String method=f.getDescription().getMethodName();
            int index=Integer.parseInt(method.substring(method.lastIndexOf('[')+1,method.lastIndexOf(']')));
            failed[index]=true;
        }
        for(int i=0;i<13;i++){
            System.out.println("HashingBox"+i+": "+(failed[i]?"检出错误":"通过当前测试（size=0冲突另报）"));
            if(failed[i]!=(i!=10))throw new AssertionError("分类与核查不一致："+i);
        }
        System.out.println("原始参数化测试：运行="+result.getRunCount()+"，失败="+result.getFailureCount()+"（预期检出缺陷）");
        // 直接把0加进正式测试会令所有原实现失败；单独验证并报告，不改原实现。
        for(HashingBox box:Q2Test.getImplementations()){
            try{box.hash(new Thing(Material.STEEL,0,Shape.CIRCLE,"A"));}
            catch(ComponentOutOfRangeException e){throw new AssertionError("原实现边界行为发生变化，请重审0用例",e);}
        }
        System.out.println("已确认：13个原实现都未对size=0抛出规定异常。正式测试避开此争议边界，不宣称完全符合README。");
    }
}
