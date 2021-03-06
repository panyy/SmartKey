package cn.vove7.smartkey.collections

/**
 * # ObserveableSet
 *
 * Created on 2020/5/26
 * @author Vove
 */
class ObserveableSet<T>(val src: MutableSet<T>, val onUpdate: () -> Unit) : MutableSet<T> by src {
    override fun toString(): String {
        return src.toString()
    }

    override fun add(element: T): Boolean = src.add(element).also { onUpdate() }

    override fun addAll(elements: Collection<T>): Boolean = src.addAll(elements).also { onUpdate() }

    override fun clear() = src.clear().also { onUpdate() }

    override fun remove(element: T): Boolean = src.remove(element).also { onUpdate() }

    override fun removeAll(elements: Collection<T>): Boolean =
        src.removeAll(elements).also { onUpdate() }

    override fun retainAll(elements: Collection<T>): Boolean =
        src.retainAll(elements).also { onUpdate() }
}