import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
/** 用同一份正式测试验证正确参考树和题目给定的不平衡样例；不改提交文件。 */
public class LocalValidation {
    public static void main(String[] args){
        AVLTreeTest.treeNodeRule=new TreeNodeTestRule(AVLVerification.ReferenceAVLTree.class);
        Result correct=JUnitCore.runClasses(AVLTreeTest.class);
        if(!correct.wasSuccessful())throw new AssertionError(correct.getFailures().toString());
        System.out.println("正确参考AVL：正式测试通过（"+correct.getRunCount()+"个参数化用例，7个节点）。");
        AVLTreeTest.treeNodeRule=new TreeNodeTestRule(SampleAVLTree.class);
        Result wrong=JUnitCore.runClasses(AVLTreeTest.class);
        if(wrong.wasSuccessful())throw new AssertionError("未识别原样例的不平衡问题");
        System.out.println("原SampleAVLTree：正式测试失败，成功检出错误。未修改原样例来凑绿色测试。");
        AVLVerification.main(new String[]{"--search"});
    }
}
