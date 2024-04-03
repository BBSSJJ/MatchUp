"use client"
import Lottie from 'react-lottie-player'
import loading from '../../../public/loading3.json'
import styles from './styles.module.css'

export default function Loading() {
  return (
    <div className={styles.center}>
      <Lottie
        loop
        animationData={loading}
        play
        style={{ width: 500, height: 500 }}
      />
    </div>
    
  )
}