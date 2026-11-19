"""只检查 Tokenizer 部分；不把尚未解答的 Parser 等部分算作已完成。"""
from pathlib import Path
import os
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parent
LIB = ROOT.parent / '1Test题目 汇总' / 'lib'
JDK = Path('/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home/bin')
JAVAC = str(JDK / 'javac') if JDK.exists() else shutil.which('javac')
JAVA = str(JDK / 'java') if JDK.exists() else shutil.which('java')
CASES = [
    ('01_', None, 'Q3Test', 'tokenise_'),
    ('02_', None, 'Q3Test', 'tokenise_'),
    ('03_', None, 'TokeniserParserTest', 'tokenise_'),
    ('04_', ['Q1/QueryTokenizer.java', 'Q1/QueryToken/QToken.java', 'Q1/QueryToken/QTokenType.java', 'Q1/Triples/TripleComponent.java', 'Tests/QTokenizerTest.java'], 'QTokenizerTest', ''),
    ('05_', ['MDTokenizer.java', 'MDToken.java', 'MDTokenType.java', 'MDTokenizerTest.java'], 'Q2.MDTokenizerTest', ''),
    ('06_', ['Tokeniser.java', 'Token.java', 'TokeniserTest.java'], 'TokeniserTest', ''),
    ('07_', ['Tokeniser.java', 'Token.java', 'TokeniserTest.java'], 'TokeniserTest', ''),
    ('08_', ['MyTokenizer.java', 'Tokenizer.java', 'Token.java', 'MyTokenizerTest.java'], 'MyTokenizerTest', ''),
    ('09_', ['Tokeniser.java', 'Token.java', 'Customer.java', 'TokeniserTest.java'], 'TokeniserTest', ''),
    ('10_', ['Tokenizer.java', 'Token.java', 'TokenizerTest.java'], 'parser.TokenizerTest', ''),
    ('11_', ['Tokenizer.java', 'Token.java', 'TokenizerTest.java'], 'TokenizerTest', ''),
]
RUNNER = '''import org.junit.runner.*;
import java.lang.reflect.Method;
public class RunTokenizerTests {
    public static void main(String[] args) throws Exception {
        Class<?> test = Class.forName(args[0]);
        int count = 0;
        int failed = 0;
        for (Method method : test.getMethods()) {
            if (method.isAnnotationPresent(org.junit.Test.class) && method.getName().startsWith(args[1])) {
                Result result = new JUnitCore().run(Request.method(test, method.getName()));
                count += result.getRunCount();
                failed += result.getFailureCount();
                for (Object failure : result.getFailures()) System.out.println(failure);
            }
        }
        System.out.println("tests=" + count + ", failures=" + failed);
        if (count == 0 || failed != 0) System.exit(1);
    }
}
'''
for prefix, files, test, method_prefix in CASES:
    folder = next(ROOT.glob(prefix + '*'))
    sources = sorted(folder.glob('*.java')) if files is None else [folder / name for name in files]
    with tempfile.TemporaryDirectory(prefix='tokenizer-answer-') as temp:
        runner = Path(temp) / 'RunTokenizerTests.java'
        runner.write_text(RUNNER)
        cp = os.pathsep.join([temp, str(LIB / '*')])
        subprocess.run([JAVAC, '-encoding', 'UTF-8', '-cp', cp, '-d', temp, *map(str, sources), str(runner)], check=True)
        result = subprocess.run([JAVA, '-cp', cp, 'RunTokenizerTests', test, method_prefix], text=True, capture_output=True)
        print(folder.name + ': ' + result.stdout.strip(), flush=True)
        if result.returncode:
            print(result.stderr)
            raise SystemExit(result.returncode)
