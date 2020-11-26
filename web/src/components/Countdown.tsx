import React, { useEffect } from "react";

import { motion, useAnimation } from "framer-motion";
import { FunctionComponent } from "react";

import styles from "./Countdown.module.css";

const Countdown: FunctionComponent<{
  onCountdownEnded?: () => void | null;
}> = ({ onCountdownEnded }) => {
  const threeControls = useAnimation();
  const twoControls = useAnimation();
  const oneControls = useAnimation();
  const goControls = useAnimation();

  useEffect(() => {
    (async () => {
      await threeControls.start({
        opacity: 1,
        y: -2,
        scale: 1.2,
        transition: { duration: 0.2 },
      });
      await threeControls.start({
        opacity: 0,
        y: -10,
        scale: 1,
        transition: { duration: 0.8 },
      });
      await twoControls.start({
        opacity: 1,
        y: -2,
        scale: 1.2,
        transition: { duration: 0.2 },
      });
      await twoControls.start({
        opacity: 0,
        y: -10,
        scale: 1,
        transition: { duration: 0.8 },
      });
      await oneControls.start({
        opacity: 1,
        y: -2,
        scale: 1.2,
        transition: { duration: 0.2 },
      });
      await oneControls.start({
        opacity: 0,
        y: -10,
        scale: 1,
        transition: { duration: 0.8 },
      });
      await goControls.start({
        opacity: 1,
        y: -2,
        scale: 1.2,
        transition: { duration: 0.2 },
      });
      await goControls.start({
        opacity: 0,
        y: -10,
        scale: 1,
        transition: { duration: 0.8 },
      });

      if (onCountdownEnded) onCountdownEnded();
    })();
  }, []);

  return (
    <div className={styles.root}>
      <motion.div className={styles.digit} animate={threeControls}>
        3
      </motion.div>
      <motion.div className={styles.digit} animate={twoControls}>
        2
      </motion.div>
      <motion.div className={styles.digit} animate={oneControls}>
        1
      </motion.div>
      <motion.div className={styles.digit} animate={goControls}>
        GO!
      </motion.div>
    </div>
  );
};

export default Countdown;
