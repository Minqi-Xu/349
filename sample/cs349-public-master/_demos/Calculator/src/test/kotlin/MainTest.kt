import org.junit.Test

class MainTest {
    @Test
    fun addition() {
        assert(Main.Expr(0, Main.Expr.OP.ADD, 0).calculate() == 0)
        assert(Main.Expr(0, Main.Expr.OP.ADD, 2_147_483_647).calculate() == 2_147_483_647)
        assert(Main.Expr(0, Main.Expr.OP.ADD, -2_147_483_647).calculate() == -2_147_483_647)
        assert(Main.Expr(1, Main.Expr.OP.ADD, -2_147_483_648).calculate() == -2_147_483_647)
    }

    @Test
    fun multiplication() {
        println("Multiplication tests")
        assert(Main.Expr(0, Main.Expr.OP.MUL, 0).calculate() == 0)
        assert(Main.Expr(0, Main.Expr.OP.MUL, 5).calculate() == 0)
        assert(Main.Expr(5, Main.Expr.OP.MUL, 0).calculate() == 0)
        assert(Main.Expr(1, Main.Expr.OP.MUL, 0).calculate() == 0)
        assert(Main.Expr(1, Main.Expr.OP.MUL, 5).calculate() == 5)
        assert(Main.Expr(5, Main.Expr.OP.MUL, 1).calculate() == 5)
        assert(Main.Expr(1, Main.Expr.OP.MUL, 2_147_483_647).calculate() == 2_147_483_647)
        assert(Main.Expr(1, Main.Expr.OP.MUL, -2_147_483_648).calculate() == -2_147_483_648)
    }
}