#ifndef _RANDOM_H_
#define _RANDOM_H_
// Uses multiply with carry described here:
// http://en.wikipedia.org/wiki/Random_number_generation
unsigned int m_w = 20;    /* must not be zero, nor 0x464fffff */
unsigned int m_z = 15;    /* must not be zero, nor 0x9068ffff */

unsigned int get_random()
{
    m_z = 36969 * (m_z & 65535) + (m_z >> 16);
    m_w = 18000 * (m_w & 65535) + (m_w >> 16);
    return (m_z << 16) + m_w;  /* 32-bit result */
}
#endif
