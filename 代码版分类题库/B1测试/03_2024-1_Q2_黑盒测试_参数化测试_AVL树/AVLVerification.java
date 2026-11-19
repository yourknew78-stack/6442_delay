import java.util.*;
/** 本地辅助，不是待测实现。main打印参考前序；--search穷举四类旋转的最少总节点。 */
public class AVLVerification {
    public static int rotations;
    public static int disabled; // 仅用于辅助验证：故意禁用一种旋转，0表示全部正常。
    public static class ReferenceAVLTree extends AVLTree {
        public ReferenceAVLTree(int value){super(value);}
        public ReferenceAVLTree(int value,AVLTree l,AVLTree r){super(value,l,r);}
        @Override protected ReferenceAVLTree createNode(int value,AVLTree l,AVLTree r){
            return new ReferenceAVLTree(value,l,r);
        }
        @Override public AVLTree insert(Integer x){
            if(x.equals(value))return this;
            AVLTree l=leftNode,r=rightNode;
            if(x<value) l=l==null?createNode(x,null,null):l.insert(x);
            else r=r==null?createNode(x,null,null):r.insert(x);
            AVLTree root=createNode(value,l,r); // 每次返回新树，不修改旧树
            int balance=root.getBalanceFactor();
            if(balance>1){
                int kind=x<l.value?1:4; rotations|=kind; // LL=1, LR=4
                if(disabled==kind)return root;
                if(kind==4) root=createNode(value,l.leftRotate(),r);
                return root.rightRotate();
            }
            if(balance< -1){
                int kind=x>r.value?2:8; rotations|=kind; // RR=2, RL=8
                if(disabled==kind)return root;
                if(kind==8) root=createNode(value,l,r.rightRotate());
                return root.leftRotate();
            }
            return root;
        }
    }
    private static void check(AVLTree t,Integer low,Integer high){
        if(t==null)return;
        if((low!=null&&t.value<=low)||(high!=null&&t.value>=high)||Math.abs(t.getBalanceFactor())>1)
            throw new AssertionError("不满足BST/AVL性质");
        check(t.leftNode,low,t.value);check(t.rightNode,t.value,high);
    }
    private static AVLTree build(int[] seq){
        AVLTree t=new ReferenceAVLTree(seq[0]);
        for(int i=1;i<seq.length;i++)t=t.insert(seq[i]);
        return t;
    }
    private static final int[] cheapest=new int[16];
    private static void permutations(int[] a,int pos){
        if(pos==a.length){
            rotations=0;build(a);
            cheapest[rotations]=Math.min(cheapest[rotations],a.length);
            return;
        }
        for(int j=pos;j<a.length;j++){
            int x=a[pos];a[pos]=a[j];a[j]=x;
            permutations(a,pos+1);
            x=a[pos];a[pos]=a[j];a[j]=x;
        }
    }
    public static void main(String[] args){
        int[] seq={1,7,2,3,4,6,5};
        AVLTree tree=new ReferenceAVLTree(seq[0]);rotations=0;
        System.out.println("插入 1 => "+tree.preOrder());
        for(int i=1;i<seq.length;i++){
            List<Integer> old=tree.preOrder();AVLTree previous=tree;
            rotations=0;tree=tree.insert(seq[i]);check(tree,null,null);
            if(!old.equals(previous.preOrder()))throw new AssertionError("修改了旧树");
            String event=rotations==1?"LL":rotations==2?"RR":rotations==4?"LR":rotations==8?"RL":"无";
            System.out.println("插入 "+seq[i]+" => "+tree.preOrder()+"，旋转="+event);
        }
        System.out.println("expected="+tree.preOrder());
        for(int fault:new int[]{1,2,4,8}){
            disabled=fault;
            try{
                List<Integer> actual=build(seq).preOrder();
                System.out.println("禁用旋转"+fault+" => "+actual+" 检出="+!actual.equals(tree.preOrder()));
                if(actual.equals(tree.preOrder()))throw new AssertionError("未检出禁用旋转"+fault);
            }catch(NullPointerException e){System.out.println("禁用旋转"+fault+" 导致异常，已检出");}
        }
        disabled=0;
        if(args.length>0&&args[0].equals("--search")){
            Arrays.fill(cheapest,999);cheapest[0]=0;
            for(int n=1;n<=7;n++){
                int[] a=new int[n];for(int i=0;i<n;i++)a[i]=i+1;
                permutations(a,0);
                System.out.println("已穷举长度"+n+"的所有不同键相对次序");
            }
            int[] cost=cheapest.clone();
            for(int round=0;round<4;round++)
                for(int a=0;a<16;a++)for(int b=0;b<16;b++)cost[a|b]=Math.min(cost[a|b],cost[a]+cheapest[b]);
            System.out.println("覆盖LL/RR/LR/RL的最少总节点="+cost[15]);
            if(cost[15]!=7)throw new AssertionError("最少节点核查失败");
            System.out.println("此结论只针对四类插入旋转覆盖，不证明能识别所有任意错误实现或隐藏评分规则。");
        }
    }
}
