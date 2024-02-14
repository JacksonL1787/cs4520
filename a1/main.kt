sealed class Ticket {
    abstract var price: Int
 
    class Vip(var name: String, override var price: Int) : Ticket()

    class Student(var uniName: String, var firstName: String, var lastName: String, override var price: Int) : Ticket()

    class Regular(var name: String, override var price: Int, var isDiscounted: Boolean) : Ticket()
}

class TicketSystem {
    val tickets = mutableListOf<Ticket>()

    private fun processTickets(ticket: Ticket) {
        tickets.add(ticket)
        when (ticket) {
        	is Ticket.Vip -> println("New VIP Ticket: Name - ${ticket.name}, Price - ${ticket.price}")
        	is Ticket.Student -> println("New Student Ticket: University - ${ticket.uniName}, First Name - ${ticket.firstName}, Last Name - ${ticket.lastName}, Price - ${ticket.price}")
        	is Ticket.Regular -> println("New Regular Ticket: Name - ${ticket.name}, Price - ${ticket.price}, Discounted - ${ticket.isDiscounted}")
   		}
    }

    inline fun <reified T : Ticket> getFilteredTickets(criteria: (T) -> Boolean): List<T> {
        return tickets.filterIsInstance<T>().filter { criteria(it) }
    }

    fun generateVipTicket(
        vipName: String,
        price: Int,
    ): Ticket {
        val t = Ticket.Vip(vipName, price)
        processTickets(t)
        return t
    }

    fun generateStudentTicket(
        uniName: String,
        studentFirstName: String,
        studentLastName: String,
        price: Int,
    ): Ticket {
        val t = Ticket.Student(uniName, studentFirstName, studentLastName, price)
        processTickets(t)
        return t
    }

    fun generateRegularTicket(
        name: String,
        price: Int,
        isDiscounted: Boolean,
    ): Ticket {
        val t = Ticket.Regular(name, price, isDiscounted)
        processTickets(t)
        return t
    }
}

fun TicketSystem.getCountOfVipTickets(): Int {
    return tickets.filterIsInstance<Ticket.Vip>().count()
}

fun TicketSystem.getTotalPriceForRegularTickets(): Int {
    return tickets.filterIsInstance<Ticket.Regular>().sumOf { it.price }
}

fun TicketSystem.getHighestPriceForEachTickets(): List<Ticket> {
    return listOf(
        tickets.filterIsInstance<Ticket.Vip>().maxByOrNull { it.price },
        tickets.filterIsInstance<Ticket.Student>().maxByOrNull { it.price },
        tickets.filterIsInstance<Ticket.Regular>().maxByOrNull { it.price },
    ).filterNotNull()
}