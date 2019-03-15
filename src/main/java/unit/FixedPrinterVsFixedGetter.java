/*
 * Copyright 2018 Upyter
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package unit;

import java.io.IOException;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.RunnerException;

/*
Benchmark      Mode  Cnt         Score         Error  Units
Test.getter   thrpt    5  46737378,777 ±  198278,130  ops/s
Test.printer  thrpt    5  45811861,590 ± 2072623,675  ops/s
 */

/**
 * A benchmark to compare {@link unit.getter.FixPos} with
 * {@link unit.printer.FixPos}.
 */
@State(Scope.Thread)
public class FixedPrinterVsFixedGetter {
    private unit.getter.Pos getter;
    private unit.printer.Pos printer;

    public static void main(final String[] args)
        throws IOException, RunnerException
    {
        Main.main(args);
    }

    @Setup
    public void setup() {
        getter = new unit.getter.FixPos(24, 439);
        printer = new unit.printer.FixPos(53, 548);
    }

    @Benchmark
    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @BenchmarkMode(Mode.Throughput)
    public void getter() {
        Math.nextAfter(getter.y(), Math.sin(getter.x()));
    }

    @Benchmark
    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2)
    @BenchmarkMode(Mode.Throughput)
    public void printer() {
        printer.result(
            (x, y) -> Math.nextAfter(y, Math.sin(x))
        );
    }
}
