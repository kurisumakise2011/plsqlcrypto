package com.pl.sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * ГОСТ 34.11-2012
 */
public class HashFunction {
   private final byte[][] A = {
         {(byte) 0x8e, (byte) 0x20, (byte) 0xfa, (byte) 0xa7, (byte) 0x2b, (byte) 0xa0, (byte) 0xb4, (byte) 0x70}, {(byte) 0x47, (byte) 0x10, (byte) 0x7d, (byte) 0xdd, (byte) 0x9b, (byte) 0x50, (byte) 0x5a, (byte) 0x38},
         {(byte) 0xad, (byte) 0x08, (byte) 0xb0, (byte) 0xe0, (byte) 0xc3, (byte) 0x28, (byte) 0x2d, (byte) 0x1c}, {(byte) 0xd8, (byte) 0x04, (byte) 0x58, (byte) 0x70, (byte) 0xef, (byte) 0x14, (byte) 0x98, (byte) 0x0e},
         {(byte) 0x6c, (byte) 0x02, (byte) 0x2c, (byte) 0x38, (byte) 0xf9, (byte) 0x0a, (byte) 0x4c, (byte) 0x07}, {(byte) 0x36, (byte) 0x01, (byte) 0x16, (byte) 0x1c, (byte) 0xf2, (byte) 0x05, (byte) 0x26, (byte) 0x8d},
         {(byte) 0x1b, (byte) 0x8e, (byte) 0x0b, (byte) 0x0e, (byte) 0x79, (byte) 0x8c, (byte) 0x13, (byte) 0xc8}, {(byte) 0x83, (byte) 0x47, (byte) 0x8b, (byte) 0x07, (byte) 0xb2, (byte) 0x46, (byte) 0x87, (byte) 0x64},
         {(byte) 0xa0, (byte) 0x11, (byte) 0xd3, (byte) 0x80, (byte) 0x81, (byte) 0x8e, (byte) 0x8f, (byte) 0x40}, {(byte) 0x50, (byte) 0x86, (byte) 0xe7, (byte) 0x40, (byte) 0xce, (byte) 0x47, (byte) 0xc9, (byte) 0x20},
         {(byte) 0x28, (byte) 0x43, (byte) 0xfd, (byte) 0x20, (byte) 0x67, (byte) 0xad, (byte) 0xea, (byte) 0x10}, {(byte) 0x14, (byte) 0xaf, (byte) 0xf0, (byte) 0x10, (byte) 0xbd, (byte) 0xd8, (byte) 0x75, (byte) 0x08},
         {(byte) 0x0a, (byte) 0xd9, (byte) 0x78, (byte) 0x08, (byte) 0xd0, (byte) 0x6c, (byte) 0xb4, (byte) 0x04}, {(byte) 0x05, (byte) 0xe2, (byte) 0x3c, (byte) 0x04, (byte) 0x68, (byte) 0x36, (byte) 0x5a, (byte) 0x02},
         {(byte) 0x8c, (byte) 0x71, (byte) 0x1e, (byte) 0x02, (byte) 0x34, (byte) 0x1b, (byte) 0x2d, (byte) 0x01}, {(byte) 0x46, (byte) 0xb6, (byte) 0x0f, (byte) 0x01, (byte) 0x1a, (byte) 0x83, (byte) 0x98, (byte) 0x8e},
         {(byte) 0x90, (byte) 0xda, (byte) 0xb5, (byte) 0x2a, (byte) 0x38, (byte) 0x7a, (byte) 0xe7, (byte) 0x6f}, {(byte) 0x48, (byte) 0x6d, (byte) 0xd4, (byte) 0x15, (byte) 0x1c, (byte) 0x3d, (byte) 0xfd, (byte) 0xb9},
         {(byte) 0x24, (byte) 0xb8, (byte) 0x6a, (byte) 0x84, (byte) 0x0e, (byte) 0x90, (byte) 0xf0, (byte) 0xd2}, {(byte) 0x12, (byte) 0x5c, (byte) 0x35, (byte) 0x42, (byte) 0x07, (byte) 0x48, (byte) 0x78, (byte) 0x69},
         {(byte) 0x09, (byte) 0x2e, (byte) 0x94, (byte) 0x21, (byte) 0x8d, (byte) 0x24, (byte) 0x3c, (byte) 0xba}, {(byte) 0x8a, (byte) 0x17, (byte) 0x4a, (byte) 0x9e, (byte) 0xc8, (byte) 0x12, (byte) 0x1e, (byte) 0x5d},
         {(byte) 0x45, (byte) 0x85, (byte) 0x25, (byte) 0x4f, (byte) 0x64, (byte) 0x09, (byte) 0x0f, (byte) 0xa0}, {(byte) 0xac, (byte) 0xcc, (byte) 0x9c, (byte) 0xa9, (byte) 0x32, (byte) 0x8a, (byte) 0x89, (byte) 0x50},
         {(byte) 0x9d, (byte) 0x4d, (byte) 0xf0, (byte) 0x5d, (byte) 0x5f, (byte) 0x66, (byte) 0x14, (byte) 0x51}, {(byte) 0xc0, (byte) 0xa8, (byte) 0x78, (byte) 0xa0, (byte) 0xa1, (byte) 0x33, (byte) 0x0a, (byte) 0xa6},
         {(byte) 0x60, (byte) 0x54, (byte) 0x3c, (byte) 0x50, (byte) 0xde, (byte) 0x97, (byte) 0x05, (byte) 0x53}, {(byte) 0x30, (byte) 0x2a, (byte) 0x1e, (byte) 0x28, (byte) 0x6f, (byte) 0xc5, (byte) 0x8c, (byte) 0xa7},
         {(byte) 0x18, (byte) 0x15, (byte) 0x0f, (byte) 0x14, (byte) 0xb9, (byte) 0xec, (byte) 0x46, (byte) 0xdd}, {(byte) 0x0c, (byte) 0x84, (byte) 0x89, (byte) 0x0a, (byte) 0xd2, (byte) 0x76, (byte) 0x23, (byte) 0xe0},
         {(byte) 0x06, (byte) 0x42, (byte) 0xca, (byte) 0x05, (byte) 0x69, (byte) 0x3b, (byte) 0x9f, (byte) 0x70}, {(byte) 0x03, (byte) 0x21, (byte) 0x65, (byte) 0x8c, (byte) 0xba, (byte) 0x93, (byte) 0xc1, (byte) 0x38},
         {(byte) 0x86, (byte) 0x27, (byte) 0x5d, (byte) 0xf0, (byte) 0x9c, (byte) 0xe8, (byte) 0xaa, (byte) 0xa8}, {(byte) 0x43, (byte) 0x9d, (byte) 0xa0, (byte) 0x78, (byte) 0x4e, (byte) 0x74, (byte) 0x55, (byte) 0x54},
         {(byte) 0xaf, (byte) 0xc0, (byte) 0x50, (byte) 0x3c, (byte) 0x27, (byte) 0x3a, (byte) 0xa4, (byte) 0x2a}, {(byte) 0xd9, (byte) 0x60, (byte) 0x28, (byte) 0x1e, (byte) 0x9d, (byte) 0x1d, (byte) 0x52, (byte) 0x15},
         {(byte) 0xe2, (byte) 0x30, (byte) 0x14, (byte) 0x0f, (byte) 0xc0, (byte) 0x80, (byte) 0x29, (byte) 0x84}, {(byte) 0x71, (byte) 0x18, (byte) 0x0a, (byte) 0x89, (byte) 0x60, (byte) 0x40, (byte) 0x9a, (byte) 0x42},
         {(byte) 0xb6, (byte) 0x0c, (byte) 0x05, (byte) 0xca, (byte) 0x30, (byte) 0x20, (byte) 0x4d, (byte) 0x21}, {(byte) 0x5b, (byte) 0x06, (byte) 0x8c, (byte) 0x65, (byte) 0x18, (byte) 0x10, (byte) 0xa8, (byte) 0x9e},
         {(byte) 0x45, (byte) 0x6c, (byte) 0x34, (byte) 0x88, (byte) 0x7a, (byte) 0x38, (byte) 0x05, (byte) 0xb9}, {(byte) 0xac, (byte) 0x36, (byte) 0x1a, (byte) 0x44, (byte) 0x3d, (byte) 0x1c, (byte) 0x8c, (byte) 0xd2},
         {(byte) 0x56, (byte) 0x1b, (byte) 0x0d, (byte) 0x22, (byte) 0x90, (byte) 0x0e, (byte) 0x46, (byte) 0x69}, {(byte) 0x2b, (byte) 0x83, (byte) 0x88, (byte) 0x11, (byte) 0x48, (byte) 0x07, (byte) 0x23, (byte) 0xba},
         {(byte) 0x9b, (byte) 0xcf, (byte) 0x44, (byte) 0x86, (byte) 0x24, (byte) 0x8d, (byte) 0x9f, (byte) 0x5d}, {(byte) 0xc3, (byte) 0xe9, (byte) 0x22, (byte) 0x43, (byte) 0x12, (byte) 0xc8, (byte) 0xc1, (byte) 0xa0},
         {(byte) 0xef, (byte) 0xfa, (byte) 0x11, (byte) 0xaf, (byte) 0x09, (byte) 0x64, (byte) 0xee, (byte) 0x50}, {(byte) 0xf9, (byte) 0x7d, (byte) 0x86, (byte) 0xd9, (byte) 0x8a, (byte) 0x32, (byte) 0x77, (byte) 0x28},
         {(byte) 0xe4, (byte) 0xfa, (byte) 0x20, (byte) 0x54, (byte) 0xa8, (byte) 0x0b, (byte) 0x32, (byte) 0x9c}, {(byte) 0x72, (byte) 0x7d, (byte) 0x10, (byte) 0x2a, (byte) 0x54, (byte) 0x8b, (byte) 0x19, (byte) 0x4e},
         {(byte) 0x39, (byte) 0xb0, (byte) 0x08, (byte) 0x15, (byte) 0x2a, (byte) 0xcb, (byte) 0x82, (byte) 0x27}, {(byte) 0x92, (byte) 0x58, (byte) 0x04, (byte) 0x84, (byte) 0x15, (byte) 0xeb, (byte) 0x41, (byte) 0x9d},
         {(byte) 0x49, (byte) 0x2c, (byte) 0x02, (byte) 0x42, (byte) 0x84, (byte) 0xfb, (byte) 0xae, (byte) 0xc0}, {(byte) 0xaa, (byte) 0x16, (byte) 0x01, (byte) 0x21, (byte) 0x42, (byte) 0xf3, (byte) 0x57, (byte) 0x60},
         {(byte) 0x55, (byte) 0x0b, (byte) 0x8e, (byte) 0x9e, (byte) 0x21, (byte) 0xf7, (byte) 0xa5, (byte) 0x30}, {(byte) 0xa4, (byte) 0x8b, (byte) 0x47, (byte) 0x4f, (byte) 0x9e, (byte) 0xf5, (byte) 0xdc, (byte) 0x18},
         {(byte) 0x70, (byte) 0xa6, (byte) 0xa5, (byte) 0x6e, (byte) 0x24, (byte) 0x40, (byte) 0x59, (byte) 0x8e}, {(byte) 0x38, (byte) 0x53, (byte) 0xdc, (byte) 0x37, (byte) 0x12, (byte) 0x20, (byte) 0xa2, (byte) 0x47},
         {(byte) 0x1c, (byte) 0xa7, (byte) 0x6e, (byte) 0x95, (byte) 0x09, (byte) 0x10, (byte) 0x51, (byte) 0xad}, {(byte) 0x0e, (byte) 0xdd, (byte) 0x37, (byte) 0xc4, (byte) 0x8a, (byte) 0x08, (byte) 0xa6, (byte) 0xd8},
         {(byte) 0x07, (byte) 0xe0, (byte) 0x95, (byte) 0x62, (byte) 0x45, (byte) 0x04, (byte) 0x53, (byte) 0x6c}, {(byte) 0x8d, (byte) 0x70, (byte) 0xc4, (byte) 0x31, (byte) 0xac, (byte) 0x02, (byte) 0xa7, (byte) 0x36},
         {(byte) 0xc8, (byte) 0x38, (byte) 0x62, (byte) 0x96, (byte) 0x56, (byte) 0x01, (byte) 0xdd, (byte) 0x1b}, {(byte) 0x64, (byte) 0x1c, (byte) 0x31, (byte) 0x4b, (byte) 0x2b, (byte) 0x8e, (byte) 0xe0, (byte) 0x83}
   };

   private final byte[][] C = {
         {
               (byte) 0xb1, (byte) 0x08, (byte) 0x5b, (byte) 0xda, (byte) 0x1e, (byte) 0xca, (byte) 0xda, (byte) 0xe9, (byte) 0xeb, (byte) 0xcb, (byte) 0x2f, (byte) 0x81, (byte) 0xc0, (byte) 0x65, (byte) 0x7c, (byte) 0x1f,
               (byte) 0x2f, (byte) 0x6a, (byte) 0x76, (byte) 0x43, (byte) 0x2e, (byte) 0x45, (byte) 0xd0, (byte) 0x16, (byte) 0x71, (byte) 0x4e, (byte) 0xb8, (byte) 0x8d, (byte) 0x75, (byte) 0x85, (byte) 0xc4, (byte) 0xfc,
               (byte) 0x4b, (byte) 0x7c, (byte) 0xe0, (byte) 0x91, (byte) 0x92, (byte) 0x67, (byte) 0x69, (byte) 0x01, (byte) 0xa2, (byte) 0x42, (byte) 0x2a, (byte) 0x08, (byte) 0xa4, (byte) 0x60, (byte) 0xd3, (byte) 0x15,
               (byte) 0x05, (byte) 0x76, (byte) 0x74, (byte) 0x36, (byte) 0xcc, (byte) 0x74, (byte) 0x4d, (byte) 0x23, (byte) 0xdd, (byte) 0x80, (byte) 0x65, (byte) 0x59, (byte) 0xf2, (byte) 0xa6, (byte) 0x45, (byte) 0x07
         },
         {
               (byte) 0x6f, (byte) 0xa3, (byte) 0xb5, (byte) 0x8a, (byte) 0xa9, (byte) 0x9d, (byte) 0x2f, (byte) 0x1a, (byte) 0x4f, (byte) 0xe3, (byte) 0x9d, (byte) 0x46, (byte) 0x0f, (byte) 0x70, (byte) 0xb5, (byte) 0xd7,
               (byte) 0xf3, (byte) 0xfe, (byte) 0xea, (byte) 0x72, (byte) 0x0a, (byte) 0x23, (byte) 0x2b, (byte) 0x98, (byte) 0x61, (byte) 0xd5, (byte) 0x5e, (byte) 0x0f, (byte) 0x16, (byte) 0xb5, (byte) 0x01, (byte) 0x31,
               (byte) 0x9a, (byte) 0xb5, (byte) 0x17, (byte) 0x6b, (byte) 0x12, (byte) 0xd6, (byte) 0x99, (byte) 0x58, (byte) 0x5c, (byte) 0xb5, (byte) 0x61, (byte) 0xc2, (byte) 0xdb, (byte) 0x0a, (byte) 0xa7, (byte) 0xca,
               (byte) 0x55, (byte) 0xdd, (byte) 0xa2, (byte) 0x1b, (byte) 0xd7, (byte) 0xcb, (byte) 0xcd, (byte) 0x56, (byte) 0xe6, (byte) 0x79, (byte) 0x04, (byte) 0x70, (byte) 0x21, (byte) 0xb1, (byte) 0x9b, (byte) 0xb7
         },
         {
               (byte) 0xf5, (byte) 0x74, (byte) 0xdc, (byte) 0xac, (byte) 0x2b, (byte) 0xce, (byte) 0x2f, (byte) 0xc7, (byte) 0x0a, (byte) 0x39, (byte) 0xfc, (byte) 0x28, (byte) 0x6a, (byte) 0x3d, (byte) 0x84, (byte) 0x35,
               (byte) 0x06, (byte) 0xf1, (byte) 0x5e, (byte) 0x5f, (byte) 0x52, (byte) 0x9c, (byte) 0x1f, (byte) 0x8b, (byte) 0xf2, (byte) 0xea, (byte) 0x75, (byte) 0x14, (byte) 0xb1, (byte) 0x29, (byte) 0x7b, (byte) 0x7b,
               (byte) 0xd3, (byte) 0xe2, (byte) 0x0f, (byte) 0xe4, (byte) 0x90, (byte) 0x35, (byte) 0x9e, (byte) 0xb1, (byte) 0xc1, (byte) 0xc9, (byte) 0x3a, (byte) 0x37, (byte) 0x60, (byte) 0x62, (byte) 0xdb, (byte) 0x09,
               (byte) 0xc2, (byte) 0xb6, (byte) 0xf4, (byte) 0x43, (byte) 0x86, (byte) 0x7a, (byte) 0xdb, (byte) 0x31, (byte) 0x99, (byte) 0x1e, (byte) 0x96, (byte) 0xf5, (byte) 0x0a, (byte) 0xba, (byte) 0x0a, (byte) 0xb2
         },
         {
               (byte) 0xef, (byte) 0x1f, (byte) 0xdf, (byte) 0xb3, (byte) 0xe8, (byte) 0x15, (byte) 0x66, (byte) 0xd2, (byte) 0xf9, (byte) 0x48, (byte) 0xe1, (byte) 0xa0, (byte) 0x5d, (byte) 0x71, (byte) 0xe4, (byte) 0xdd,
               (byte) 0x48, (byte) 0x8e, (byte) 0x85, (byte) 0x7e, (byte) 0x33, (byte) 0x5c, (byte) 0x3c, (byte) 0x7d, (byte) 0x9d, (byte) 0x72, (byte) 0x1c, (byte) 0xad, (byte) 0x68, (byte) 0x5e, (byte) 0x35, (byte) 0x3f,
               (byte) 0xa9, (byte) 0xd7, (byte) 0x2c, (byte) 0x82, (byte) 0xed, (byte) 0x03, (byte) 0xd6, (byte) 0x75, (byte) 0xd8, (byte) 0xb7, (byte) 0x13, (byte) 0x33, (byte) 0x93, (byte) 0x52, (byte) 0x03, (byte) 0xbe,
               (byte) 0x34, (byte) 0x53, (byte) 0xea, (byte) 0xa1, (byte) 0x93, (byte) 0xe8, (byte) 0x37, (byte) 0xf1, (byte) 0x22, (byte) 0x0c, (byte) 0xbe, (byte) 0xbc, (byte) 0x84, (byte) 0xe3, (byte) 0xd1, (byte) 0x2e
         },
         {
               (byte) 0x4b, (byte) 0xea, (byte) 0x6b, (byte) 0xac, (byte) 0xad, (byte) 0x47, (byte) 0x47, (byte) 0x99, (byte) 0x9a, (byte) 0x3f, (byte) 0x41, (byte) 0x0c, (byte) 0x6c, (byte) 0xa9, (byte) 0x23, (byte) 0x63,
               (byte) 0x7f, (byte) 0x15, (byte) 0x1c, (byte) 0x1f, (byte) 0x16, (byte) 0x86, (byte) 0x10, (byte) 0x4a, (byte) 0x35, (byte) 0x9e, (byte) 0x35, (byte) 0xd7, (byte) 0x80, (byte) 0x0f, (byte) 0xff, (byte) 0xbd,
               (byte) 0xbf, (byte) 0xcd, (byte) 0x17, (byte) 0x47, (byte) 0x25, (byte) 0x3a, (byte) 0xf5, (byte) 0xa3, (byte) 0xdf, (byte) 0xff, (byte) 0x00, (byte) 0xb7, (byte) 0x23, (byte) 0x27, (byte) 0x1a, (byte) 0x16,
               (byte) 0x7a, (byte) 0x56, (byte) 0xa2, (byte) 0x7e, (byte) 0xa9, (byte) 0xea, (byte) 0x63, (byte) 0xf5, (byte) 0x60, (byte) 0x17, (byte) 0x58, (byte) 0xfd, (byte) 0x7c, (byte) 0x6c, (byte) 0xfe, (byte) 0x57
         },
         {
               (byte) 0xae, (byte) 0x4f, (byte) 0xae, (byte) 0xae, (byte) 0x1d, (byte) 0x3a, (byte) 0xd3, (byte) 0xd9, (byte) 0x6f, (byte) 0xa4, (byte) 0xc3, (byte) 0x3b, (byte) 0x7a, (byte) 0x30, (byte) 0x39, (byte) 0xc0,
               (byte) 0x2d, (byte) 0x66, (byte) 0xc4, (byte) 0xf9, (byte) 0x51, (byte) 0x42, (byte) 0xa4, (byte) 0x6c, (byte) 0x18, (byte) 0x7f, (byte) 0x9a, (byte) 0xb4, (byte) 0x9a, (byte) 0xf0, (byte) 0x8e, (byte) 0xc6,
               (byte) 0xcf, (byte) 0xfa, (byte) 0xa6, (byte) 0xb7, (byte) 0x1c, (byte) 0x9a, (byte) 0xb7, (byte) 0xb4, (byte) 0x0a, (byte) 0xf2, (byte) 0x1f, (byte) 0x66, (byte) 0xc2, (byte) 0xbe, (byte) 0xc6, (byte) 0xb6,
               (byte) 0xbf, (byte) 0x71, (byte) 0xc5, (byte) 0x72, (byte) 0x36, (byte) 0x90, (byte) 0x4f, (byte) 0x35, (byte) 0xfa, (byte) 0x68, (byte) 0x40, (byte) 0x7a, (byte) 0x46, (byte) 0x64, (byte) 0x7d, (byte) 0x6e
         },
         {
               (byte) 0xf4, (byte) 0xc7, (byte) 0x0e, (byte) 0x16, (byte) 0xee, (byte) 0xaa, (byte) 0xc5, (byte) 0xec, (byte) 0x51, (byte) 0xac, (byte) 0x86, (byte) 0xfe, (byte) 0xbf, (byte) 0x24, (byte) 0x09, (byte) 0x54,
               (byte) 0x39, (byte) 0x9e, (byte) 0xc6, (byte) 0xc7, (byte) 0xe6, (byte) 0xbf, (byte) 0x87, (byte) 0xc9, (byte) 0xd3, (byte) 0x47, (byte) 0x3e, (byte) 0x33, (byte) 0x19, (byte) 0x7a, (byte) 0x93, (byte) 0xc9,
               (byte) 0x09, (byte) 0x92, (byte) 0xab, (byte) 0xc5, (byte) 0x2d, (byte) 0x82, (byte) 0x2c, (byte) 0x37, (byte) 0x06, (byte) 0x47, (byte) 0x69, (byte) 0x83, (byte) 0x28, (byte) 0x4a, (byte) 0x05, (byte) 0x04,
               (byte) 0x35, (byte) 0x17, (byte) 0x45, (byte) 0x4c, (byte) 0xa2, (byte) 0x3c, (byte) 0x4a, (byte) 0xf3, (byte) 0x88, (byte) 0x86, (byte) 0x56, (byte) 0x4d, (byte) 0x3a, (byte) 0x14, (byte) 0xd4, (byte) 0x93
         },
         {
               (byte) 0x9b, (byte) 0x1f, (byte) 0x5b, (byte) 0x42, (byte) 0x4d, (byte) 0x93, (byte) 0xc9, (byte) 0xa7, (byte) 0x03, (byte) 0xe7, (byte) 0xaa, (byte) 0x02, (byte) 0x0c, (byte) 0x6e, (byte) 0x41, (byte) 0x41,
               (byte) 0x4e, (byte) 0xb7, (byte) 0xf8, (byte) 0x71, (byte) 0x9c, (byte) 0x36, (byte) 0xde, (byte) 0x1e, (byte) 0x89, (byte) 0xb4, (byte) 0x44, (byte) 0x3b, (byte) 0x4d, (byte) 0xdb, (byte) 0xc4, (byte) 0x9a,
               (byte) 0xf4, (byte) 0x89, (byte) 0x2b, (byte) 0xcb, (byte) 0x92, (byte) 0x9b, (byte) 0x06, (byte) 0x90, (byte) 0x69, (byte) 0xd1, (byte) 0x8d, (byte) 0x2b, (byte) 0xd1, (byte) 0xa5, (byte) 0xc4, (byte) 0x2f,
               (byte) 0x36, (byte) 0xac, (byte) 0xc2, (byte) 0x35, (byte) 0x59, (byte) 0x51, (byte) 0xa8, (byte) 0xd9, (byte) 0xa4, (byte) 0x7f, (byte) 0x0d, (byte) 0xd4, (byte) 0xbf, (byte) 0x02, (byte) 0xe7, (byte) 0x1e
         },
         {
               (byte) 0x37, (byte) 0x8f, (byte) 0x5a, (byte) 0x54, (byte) 0x16, (byte) 0x31, (byte) 0x22, (byte) 0x9b, (byte) 0x94, (byte) 0x4c, (byte) 0x9a, (byte) 0xd8, (byte) 0xec, (byte) 0x16, (byte) 0x5f, (byte) 0xde,
               (byte) 0x3a, (byte) 0x7d, (byte) 0x3a, (byte) 0x1b, (byte) 0x25, (byte) 0x89, (byte) 0x42, (byte) 0x24, (byte) 0x3c, (byte) 0xd9, (byte) 0x55, (byte) 0xb7, (byte) 0xe0, (byte) 0x0d, (byte) 0x09, (byte) 0x84,
               (byte) 0x80, (byte) 0x0a, (byte) 0x44, (byte) 0x0b, (byte) 0xdb, (byte) 0xb2, (byte) 0xce, (byte) 0xb1, (byte) 0x7b, (byte) 0x2b, (byte) 0x8a, (byte) 0x9a, (byte) 0xa6, (byte) 0x07, (byte) 0x9c, (byte) 0x54,
               (byte) 0x0e, (byte) 0x38, (byte) 0xdc, (byte) 0x92, (byte) 0xcb, (byte) 0x1f, (byte) 0x2a, (byte) 0x60, (byte) 0x72, (byte) 0x61, (byte) 0x44, (byte) 0x51, (byte) 0x83, (byte) 0x23, (byte) 0x5a, (byte) 0xdb
         },
         {
               (byte) 0xab, (byte) 0xbe, (byte) 0xde, (byte) 0xa6, (byte) 0x80, (byte) 0x05, (byte) 0x6f, (byte) 0x52, (byte) 0x38, (byte) 0x2a, (byte) 0xe5, (byte) 0x48, (byte) 0xb2, (byte) 0xe4, (byte) 0xf3, (byte) 0xf3,
               (byte) 0x89, (byte) 0x41, (byte) 0xe7, (byte) 0x1c, (byte) 0xff, (byte) 0x8a, (byte) 0x78, (byte) 0xdb, (byte) 0x1f, (byte) 0xff, (byte) 0xe1, (byte) 0x8a, (byte) 0x1b, (byte) 0x33, (byte) 0x61, (byte) 0x03,
               (byte) 0x9f, (byte) 0xe7, (byte) 0x67, (byte) 0x02, (byte) 0xaf, (byte) 0x69, (byte) 0x33, (byte) 0x4b, (byte) 0x7a, (byte) 0x1e, (byte) 0x6c, (byte) 0x30, (byte) 0x3b, (byte) 0x76, (byte) 0x52, (byte) 0xf4,
               (byte) 0x36, (byte) 0x98, (byte) 0xfa, (byte) 0xd1, (byte) 0x15, (byte) 0x3b, (byte) 0xb6, (byte) 0xc3, (byte) 0x74, (byte) 0xb4, (byte) 0xc7, (byte) 0xfb, (byte) 0x98, (byte) 0x45, (byte) 0x9c, (byte) 0xed
         },
         {
               (byte) 0x7b, (byte) 0xcd, (byte) 0x9e, (byte) 0xd0, (byte) 0xef, (byte) 0xc8, (byte) 0x89, (byte) 0xfb, (byte) 0x30, (byte) 0x02, (byte) 0xc6, (byte) 0xcd, (byte) 0x63, (byte) 0x5a, (byte) 0xfe, (byte) 0x94,
               (byte) 0xd8, (byte) 0xfa, (byte) 0x6b, (byte) 0xbb, (byte) 0xeb, (byte) 0xab, (byte) 0x07, (byte) 0x61, (byte) 0x20, (byte) 0x01, (byte) 0x80, (byte) 0x21, (byte) 0x14, (byte) 0x84, (byte) 0x66, (byte) 0x79,
               (byte) 0x8a, (byte) 0x1d, (byte) 0x71, (byte) 0xef, (byte) 0xea, (byte) 0x48, (byte) 0xb9, (byte) 0xca, (byte) 0xef, (byte) 0xba, (byte) 0xcd, (byte) 0x1d, (byte) 0x7d, (byte) 0x47, (byte) 0x6e, (byte) 0x98,
               (byte) 0xde, (byte) 0xa2, (byte) 0x59, (byte) 0x4a, (byte) 0xc0, (byte) 0x6f, (byte) 0xd8, (byte) 0x5d, (byte) 0x6b, (byte) 0xca, (byte) 0xa4, (byte) 0xcd, (byte) 0x81, (byte) 0xf3, (byte) 0x2d, (byte) 0x1b
         },
         {
               (byte) 0x37, (byte) 0x8e, (byte) 0xe7, (byte) 0x67, (byte) 0xf1, (byte) 0x16, (byte) 0x31, (byte) 0xba, (byte) 0xd2, (byte) 0x13, (byte) 0x80, (byte) 0xb0, (byte) 0x04, (byte) 0x49, (byte) 0xb1, (byte) 0x7a,
               (byte) 0xcd, (byte) 0xa4, (byte) 0x3c, (byte) 0x32, (byte) 0xbc, (byte) 0xdf, (byte) 0x1d, (byte) 0x77, (byte) 0xf8, (byte) 0x20, (byte) 0x12, (byte) 0xd4, (byte) 0x30, (byte) 0x21, (byte) 0x9f, (byte) 0x9b,
               (byte) 0x5d, (byte) 0x80, (byte) 0xef, (byte) 0x9d, (byte) 0x18, (byte) 0x91, (byte) 0xcc, (byte) 0x86, (byte) 0xe7, (byte) 0x1d, (byte) 0xa4, (byte) 0xaa, (byte) 0x88, (byte) 0xe1, (byte) 0x28, (byte) 0x52,
               (byte) 0xfa, (byte) 0xf4, (byte) 0x17, (byte) 0xd5, (byte) 0xd9, (byte) 0xb2, (byte) 0x1b, (byte) 0x99, (byte) 0x48, (byte) 0xbc, (byte) 0x92, (byte) 0x4a, (byte) 0xf1, (byte) 0x1b, (byte) 0xd7, (byte) 0x20
         }
   };

   public static String getHash(String message) {
      return new String(new HashFunction().getHash(message.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
   }

   public byte[] getHash(byte[] message) {
      return hash(message);
   }

   private byte[] hash(byte[] message) {
      byte[] h = new byte[64];
      Arrays.fill(h, (byte) 0x01);

      byte[] N = new byte[64];
      Arrays.fill(N, (byte) 0x00);

      byte[] Sigma = new byte[64];
      Arrays.fill(Sigma, (byte) 0x00);

      byte[] v_0 = new byte[64];
      Arrays.fill(v_0, (byte) 0x00);

      byte[] v_512 = new byte[64];
      Arrays.fill(v_512, (byte) 0x00);
      v_512[63] = (byte) 0x02;

      int length = message.length * 8;
      int inc = 0;

      while (length >= 512) {
         inc++;
         byte[] tmp = Arrays.copyOfRange(message, message.length - 64 * (inc), message.length - (inc - 1) * 64);
         h = hashG(N, tmp, h);
         N = addModule(N, v_512);
         Sigma = addModule(Sigma, tmp);
         length -= 512;
      }

      message = Arrays.copyOf(message, message.length - 64 * inc);

      if (message.length < 64) {
         byte[] tmpMessage = new byte[64];
         Arrays.fill(tmpMessage, (byte) 0x00);
         tmpMessage[64 - message.length - 1] = (byte) 0x01;

         if (0 - message.length >= 0) {
            System.arraycopy(message, 64 - message.length + message.length - 64, tmpMessage, 64 - message.length, 0 - message.length);
         }

         message = tmpMessage;
      }

      h = hashG(N, message, h);

      byte[] NMessage = new byte[64];
      Arrays.fill(NMessage, (byte) 0x00);

      inc = 0;
      while (length > 0) {
         NMessage[63 - inc] = (byte) (length & 0xff);
         length >>= 8;
         inc++;
      }

      N = addModule(N, NMessage);
      Sigma = addModule(Sigma, message);

      h = hashG(v_0, N, h);
      h = hashG(v_0, Sigma, h);

      return Arrays.copyOf(h, 32);
   }

   private byte[] addModule(byte[] a, byte[] b) {
      byte[] result = new byte[64];
      int t = 0;

      for (int i = 63; i >= 0; i--) {
         t = (a[i] & 0xff) + (b[i] & 0xff) + (t >> 8);
         result[i] = (byte) (t & 0xff);
      }
      return result;
   }

   private byte[] hashX(byte[] a, byte[] b) {
      byte[] result = new byte[a.length];
      for (int i = 0; i < a.length; i++)
         result[i] = (byte) (a[i] ^ b[i]);
      return result;
   }

   private byte[] hashG(byte[] N, byte[] m, byte[] h) {

      byte[] K;
      K = hashX(h, N);
      K = hashS(K);
      K = hashP(K);
      K = hashL(K);

      byte[] t = hashE(K, m);
      t = hashX(h, t);

      return hashX(t, m);
   }

   private byte[] hashE(byte[] K, byte[] m) {
      byte[] state;

      state = hashX(K, m);
      for (int i = 0; i < 12; i++) {
         state = hashS(state);
         state = hashP(state);
         state = hashL(state);

         K = extendKey(K, i);
         state = hashX(state, K);
      }

      return state;
   }


   private byte[] extendKey(byte[] K, int i) {
      K = hashX(K, C[i]);

      K = hashS(K);
      K = hashP(K);
      K = hashL(K);

      return K;
   }

   private byte[] hashS(byte[] a) {
      byte[] result = new byte[64];

      for (int i = 0; i < 64; i++)
         result[i] = Dictionaries.PI_LEGEND[(a[i] & 0xFF)];

      return result;
   }

   private byte[] hashP(byte[] a) {
      byte[] result = new byte[64];

      for (int i = 0; i < 64; i++)
         result[i] = a[Dictionaries.TAU[i]];

      return result;
   }

   private byte[] hashL(byte[] a) {
      byte[] result = new byte[64];
      for (int i = 0; i < 8; i++) {
         for (int k = 0; k < 8; k++) {
            if ((a[i * 8 + k] & (0x80)) != 0) {
               result[8 * i] ^= A[k * 8][0];
               result[8 * i + 1] ^= A[k * 8][1];
               result[8 * i + 2] ^= A[k * 8][2];
               result[8 * i + 3] ^= A[k * 8][3];
               result[8 * i + 4] ^= A[k * 8][4];
               result[8 * i + 5] ^= A[k * 8][5];
               result[8 * i + 6] ^= A[k * 8][6];
               result[8 * i + 7] ^= A[k * 8][7];
            }
            if ((a[i * 8 + k] & (0x40)) != 0) {
               result[8 * i] ^= A[k * 8 + 1][0];
               result[8 * i + 1] ^= A[k * 8 + 1][1];
               result[8 * i + 2] ^= A[k * 8 + 1][2];
               result[8 * i + 3] ^= A[k * 8 + 1][3];
               result[8 * i + 4] ^= A[k * 8 + 1][4];
               result[8 * i + 5] ^= A[k * 8 + 1][5];
               result[8 * i + 6] ^= A[k * 8 + 1][6];
               result[8 * i + 7] ^= A[k * 8 + 1][7];
            }
            if ((a[i * 8 + k] & (0x20)) != 0) {
               result[8 * i] ^= A[k * 8 + 2][0];
               result[8 * i + 1] ^= A[k * 8 + 2][1];
               result[8 * i + 2] ^= A[k * 8 + 2][2];
               result[8 * i + 3] ^= A[k * 8 + 2][3];
               result[8 * i + 4] ^= A[k * 8 + 2][4];
               result[8 * i + 5] ^= A[k * 8 + 2][5];
               result[8 * i + 6] ^= A[k * 8 + 2][6];
               result[8 * i + 7] ^= A[k * 8 + 2][7];
            }
            if ((a[i * 8 + k] & (0x10)) != 0) {
               result[8 * i] ^= A[k * 8 + 3][0];
               result[8 * i + 1] ^= A[k * 8 + 3][1];
               result[8 * i + 2] ^= A[k * 8 + 3][2];
               result[8 * i + 3] ^= A[k * 8 + 3][3];
               result[8 * i + 4] ^= A[k * 8 + 3][4];
               result[8 * i + 5] ^= A[k * 8 + 3][5];
               result[8 * i + 6] ^= A[k * 8 + 3][6];
               result[8 * i + 7] ^= A[k * 8 + 3][7];
            }
            if ((a[i * 8 + k] & (0x8)) != 0) {
               result[8 * i] ^= A[k * 8 + 4][0];
               result[8 * i + 1] ^= A[k * 8 + 4][1];
               result[8 * i + 2] ^= A[k * 8 + 4][2];
               result[8 * i + 3] ^= A[k * 8 + 4][3];
               result[8 * i + 4] ^= A[k * 8 + 4][4];
               result[8 * i + 5] ^= A[k * 8 + 4][5];
               result[8 * i + 6] ^= A[k * 8 + 4][6];
               result[8 * i + 7] ^= A[k * 8 + 4][7];
            }
            if ((a[i * 8 + k] & (0x4)) != 0) {
               result[8 * i] ^= A[k * 8 + 5][0];
               result[8 * i + 1] ^= A[k * 8 + 5][1];
               result[8 * i + 2] ^= A[k * 8 + 5][2];
               result[8 * i + 3] ^= A[k * 8 + 5][3];
               result[8 * i + 4] ^= A[k * 8 + 5][4];
               result[8 * i + 5] ^= A[k * 8 + 5][5];
               result[8 * i + 6] ^= A[k * 8 + 5][6];
               result[8 * i + 7] ^= A[k * 8 + 5][7];
            }
            if ((a[i * 8 + k] & (0x2)) != 0) {
               result[8 * i] ^= A[k * 8 + 6][0];
               result[8 * i + 1] ^= A[k * 8 + 6][1];
               result[8 * i + 2] ^= A[k * 8 + 6][2];
               result[8 * i + 3] ^= A[k * 8 + 6][3];
               result[8 * i + 4] ^= A[k * 8 + 6][4];
               result[8 * i + 5] ^= A[k * 8 + 6][5];
               result[8 * i + 6] ^= A[k * 8 + 6][6];
               result[8 * i + 7] ^= A[k * 8 + 6][7];
            }
            if ((a[i * 8 + k] & (0x1)) != 0) {
               result[8 * i] ^= A[k * 8 + 7][0];
               result[8 * i + 1] ^= A[k * 8 + 7][1];
               result[8 * i + 2] ^= A[k * 8 + 7][2];
               result[8 * i + 3] ^= A[k * 8 + 7][3];
               result[8 * i + 4] ^= A[k * 8 + 7][4];
               result[8 * i + 5] ^= A[k * 8 + 7][5];
               result[8 * i + 6] ^= A[k * 8 + 7][6];
               result[8 * i + 7] ^= A[k * 8 + 7][7];
            }
         }
      }
      return result;
   }

   public static void main(String[] args) throws IOException {
      HashFunction hashFunction = new HashFunction();
      Files.write(Paths.get("/a_key.bin"),
            hashFunction.getHash("238y0178jk9d21j899j".getBytes(StandardCharsets.UTF_8)),
            StandardOpenOption.CREATE_NEW);
      Files.write(Paths.get("/b_key.bin"),
            hashFunction.getHash("jdhu3y2uhuj32doi32k".getBytes(StandardCharsets.UTF_8)),
            StandardOpenOption.CREATE_NEW);
   }
}
